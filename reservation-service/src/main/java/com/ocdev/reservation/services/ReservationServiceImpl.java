package com.ocdev.reservation.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
	private IDtoConverter<Booking, BookingCloseDto> _bookingCloseDtoConverter;
	
	private AmqpTemplate _rabbitTemplate;
	
	public ReservationServiceImpl(@Autowired ReservationRepository repository, 
			@Autowired HangarProxy hangarProxy,
			@Autowired IDtoConverter<Booking, BookingCreateDto> createConverter,
			@Autowired IDtoConverter<Booking, BookingCloseDto> closeConverter,
			@Autowired AmqpTemplate rabbitTemplate)
	{
		_reservationRepository = repository;
		_hangarProxy = hangarProxy;
		_bookingCreateDtoConverter = createConverter;
		_bookingCloseDtoConverter = closeConverter;
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
			throw new EntityNotFoundException("Cet a??ronef n'existe pas");
		}
		
		// check if aircraft is available
		boolean available = isAircaftAvailable(bookingCreateDto.getAircraftId(), bookingCreateDto.getDepartureTime(), bookingCreateDto.getDuration());
		if (!available) throw new AlreadyExistsException("Cet a??ronef est indisponible pour la p??riode demand??e");
		
		Booking booking = _bookingCreateDtoConverter.convertDtoToEntity(bookingCreateDto);
		booking.setAircraftId(aircraft.getId());
		
		return _reservationRepository.save(booking);
	}

	@Override
	public Collection<Booking> getAllBookings(String memberId, boolean closed) throws EntityNotFoundException
	{
		if (memberId == null) throw new EntityNotFoundException("Ce membre n'existe pas");
				
		return _reservationRepository.findAllByMemberIdAndClosedOrderByDepartureTimeAsc(memberId, closed);
	}

	@Override
	public void deleteBooking(long reservationId) throws EntityNotFoundException
	{
		Optional<Booking> reservation = _reservationRepository.findById(reservationId);
		if (!reservation.isPresent()) throw new EntityNotFoundException("Cette r??servation n'existe pas");
		
		if (reservation.get().isClosed()) throw new EntityNotFoundException("Cette r??servation est cl??tur??e");
		
		_reservationRepository.delete(reservation.get());
	}

	@Override
	public Booking closeBooking(long reservationId, BookingCloseDto bookingCloseDto) throws EntityNotFoundException, ProxyException
	{
		Optional<Booking> reservation = _reservationRepository.findById(reservationId);
		if (!reservation.isPresent()) throw new EntityNotFoundException("Cette r??servation n'existe pas");
		
		if (reservation.get().isClosed()) throw new EntityNotFoundException("Cette r??servation est cl??tur??e");
		
		// process booking
		Booking booking = _bookingCloseDtoConverter.convertDtoToEntity(bookingCloseDto);
		booking.setId(reservation.get().getId());
		booking.setMemberId(reservation.get().getMemberId());
		booking.setAircraftId(reservation.get().getAircraftId());
		booking.setClosed(true);
		
		// check if aircraft exists
		Aircraft aircraft;
		try
		{
			aircraft = _hangarProxy.getAircraftById(reservation.get().getAircraftId());
		} catch (Exception e)
		{
			throw new EntityNotFoundException("Cet a??ronef n'existe pas");
		}
		
		// send messages
		AircraftTotalTimeMessage hangarMessage = new AircraftTotalTimeMessage(aircraft.getId(),	bookingCloseDto.getDuration());
		updateAircraftTotalTime(hangarMessage);
		
		Date flightDate = Date.from(bookingCloseDto.getDepartureTime().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		RegisterFlightMessage flight = new RegisterFlightMessage(
				bookingCloseDto.getGivenName(),
				bookingCloseDto.getFamilyName(),
				bookingCloseDto.getEmail(),
				booking.getMemberId(),
				aircraft.getId(),
				booking.getDescription(),
				flightDate,
				bookingCloseDto.getDuration(),
				aircraft.getHourlyRate());
		registerFlightInFinance(flight);
		
		// close booking
		return _reservationRepository.save(booking);
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

	@Override
	public Booking getBooking(long reservationId) throws EntityNotFoundException
	{
		Optional<Booking> booking = _reservationRepository.findById(reservationId);
		
		return booking.orElseThrow(() -> new EntityNotFoundException("La r??servation n'existe pas"));
	}
}
