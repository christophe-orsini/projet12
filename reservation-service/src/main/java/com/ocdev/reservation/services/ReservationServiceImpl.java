package com.ocdev.reservation.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.converters.IDtoConverter;
import com.ocdev.reservation.dao.ReservationRepository;
import com.ocdev.reservation.dto.BookingCloseDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.errors.AlreadyExistsException;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.errors.ProxyException;
import com.ocdev.reservation.messages.AircraftTotalTimeMessage;
import com.ocdev.reservation.messages.RegisterFlightMessage;
import com.ocdev.reservation.proxies.HangarProxy;

@Service
public class ReservationServiceImpl implements ReservationService
{
	@Value("${reservation.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${hangar.rabbitmq.routingkey}")
	private String hangarRoutingkey;	
	
	@Value("${finance.rabbitmq.routingkey}")
	private String financeRoutingkey;	
	
	private ReservationRepository _reservationRepository;
	private HangarProxy _hangarProxy;
	private IDtoConverter<Booking, BookingCreateDto> _bookingCreateDtoConverter;
	
	private AmqpTemplate _rabbitTemplate;
	
	public ReservationServiceImpl(@Autowired ReservationRepository repository, 
			@Autowired HangarProxy hangarProxy,
			@Autowired IDtoConverter<Booking, BookingCreateDto> converter,
			@Autowired AmqpTemplate rabbitTemplate)
	{
		_reservationRepository = repository;
		_hangarProxy = hangarProxy;
		_bookingCreateDtoConverter = converter;
		_rabbitTemplate = rabbitTemplate;
	}

	@Override
	public boolean isAircaftAvailable(long aircraftId, LocalDateTime startTime, double duration) throws EntityNotFoundException, ProxyException
	{
		Aircraft aircraft = _hangarProxy.getAircraftById(aircraftId);
		if (!aircraft.isAvailable()) return false;
		
		int hours = (int)duration;
		int minutes = (int)((duration - hours) * 60);
        LocalDateTime arrivalTime = startTime.plusHours(hours).plusMinutes(minutes);
		Collection<Booking> reservations = _reservationRepository.findAllBookingForAircraftId(aircraft.getId(), startTime, arrivalTime);
		
		return reservations.size() == 0;
	}

	@Override
	public Collection<Aircraft> availableAircrafts(LocalDateTime startTime, double duration) throws ProxyException
	{
		Collection<Aircraft> results = new ArrayList<Aircraft>();
		Collection<Aircraft> aircrafts = _hangarProxy.getAircrafts();
		
		int hours = (int)duration;
		int minutes = (int)((duration - hours) * 60);
		LocalDateTime arrivalTime = startTime.plusHours(hours).plusMinutes(minutes);
		
		for (Aircraft aircraft : aircrafts)
		{
			if (aircraft.isAvailable())
			{
				Collection<Booking> reservations = _reservationRepository.findAllBookingForAircraftId(aircraft.getId(), startTime, arrivalTime);
				if (reservations.size() == 0)
				{
					results.add(aircraft);
				}
			}
		}
		
		return results;
	}

	@Override
	public Booking createBooking(BookingCreateDto bookingCreateDto) throws EntityNotFoundException, ProxyException, AlreadyExistsException
	{
		// TODO check if memberId exists
		if (bookingCreateDto.getMemberId() == null) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		// check if aircraft exists
		Aircraft aircraft;
		try
		{
			aircraft = _hangarProxy.getAircraftById(bookingCreateDto.getAircraftId());
		} catch (Exception e)
		{
			throw new EntityNotFoundException("Cet aéronef n'existe pas");
		}
		
		// check if aircraft is available
		boolean available = isAircaftAvailable(bookingCreateDto.getAircraftId(), bookingCreateDto.getDepartureTime(), bookingCreateDto.getDuration());
		if (!available) throw new AlreadyExistsException("Cet aéronef est déjà réservé pour la période demandée");
		
		Booking booking = _bookingCreateDtoConverter.convertDtoToEntity(bookingCreateDto);
		booking.setAircraftId(aircraft.getId());
		
		int hours = (int)bookingCreateDto.getDuration();
		int minutes = (int)((bookingCreateDto.getDuration() - hours) * 60);
		LocalDateTime startTime = bookingCreateDto.getDepartureTime();
		LocalDateTime arrivalTime = startTime.plusHours(hours).plusMinutes(minutes);
		booking.setArrivalTime(arrivalTime);
		
		return _reservationRepository.save(booking);
	}

	@Override
	public Collection<Booking> getAllBookings(String memberId) throws EntityNotFoundException
	{
		if (memberId == null) throw new EntityNotFoundException("Ce membre n'existe pas");
				
		return _reservationRepository.findAllByMemberIdAndClosed(memberId, false);
	}

	@Override
	public void deleteBooking(long reservationId) throws EntityNotFoundException
	{
		Optional<Booking> reservation = _reservationRepository.findById(reservationId);
		if (!reservation.isPresent()) throw new EntityNotFoundException("Cette réservation n'existe pas");
		
		if (reservation.get().isClosed()) throw new EntityNotFoundException("Cette réservation est clôturée");
		
		_reservationRepository.delete(reservation.get());
	}

	@Override
	public Booking closeBooking(long reservationId, BookingCloseDto bookingCloseDto) throws EntityNotFoundException, ProxyException
	{
		Optional<Booking> reservation = _reservationRepository.findById(reservationId);
		if (!reservation.isPresent()) throw new EntityNotFoundException("Cette réservation n'existe pas");
		
		if (reservation.get().isClosed()) throw new EntityNotFoundException("Cette réservation est clôturée");
		
		// Cloturer la réservation
		int hours = (int)bookingCloseDto.getDuration();
		int minutes = (int)((bookingCloseDto.getDuration() - hours) * 60);
		LocalDateTime startTime = bookingCloseDto.getDepartureTime();
		LocalDateTime arrivalTime = startTime.plusHours(hours).plusMinutes(minutes);
		reservation.get().setDepartureTime(startTime);
		reservation.get().setArrivalTime(arrivalTime);
		reservation.get().setDescription(bookingCloseDto.getDescription());
		reservation.get().setClosed(true);
		
		// check if aircraft exists
		Aircraft aircraft;
		try
		{
			aircraft = _hangarProxy.getAircraftById(reservation.get().getAircraftId());
		} catch (Exception e)
		{
			throw new EntityNotFoundException("Cet aéronef n'existe pas");
		}
		
		AircraftTotalTimeMessage hangarMessage = new AircraftTotalTimeMessage(
				aircraft.getId(),
				bookingCloseDto.getDuration());
		updateAircraftTotalTime(hangarMessage);
		
		RegisterFlightMessage flight = new RegisterFlightMessage(
				reservation.get().getMemberId(),
				aircraft.getId(),
				bookingCloseDto.getDescription(),
				bookingCloseDto.getDepartureTime(),
				bookingCloseDto.getDuration(),
				aircraft.getHourlyRate());
		registerFlightInFinance(flight);
		
		return _reservationRepository.save(reservation.get());
	}
	
	private void updateAircraftTotalTime(AircraftTotalTimeMessage message)
	{
		_rabbitTemplate.convertAndSend(exchange, hangarRoutingkey, message);
	}
	
	private void registerFlightInFinance(RegisterFlightMessage message)
	{
		_rabbitTemplate.convertAndSend(exchange, financeRoutingkey, message);
	}

	@Override
	public Collection<Booking> getAllBookings(long aircraftId, LocalDate date) throws EntityNotFoundException
	{		
		return _reservationRepository.findAllByAircraftIdAndDate(aircraftId, date);
	}
}
