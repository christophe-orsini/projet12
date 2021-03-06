package com.ocdev.airclub.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.converters.IDtoConverter;
import com.ocdev.airclub.dto.Aircraft;
import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingCloseDto;
import com.ocdev.airclub.dto.BookingCreateDto;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;
import com.ocdev.airclub.dto.BookingDisplayDto;
import com.ocdev.airclub.dto.BookingNewDto;
import com.ocdev.airclub.errors.AlreadyExistsException;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ProxyException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class BookingServiceImpl implements BookingService
{
	@Value("${app.gateway.url}")
	private String _gatewayUrl;
	
	@Autowired
	WebClient webclient;
	
	@Autowired
	private IDtoConverter<BookingCreateDto, BookingNewDto> _bookingCreateDtoConverter;
	
	@Autowired
	private IDtoConverter<Booking, BookingDisplayDto> _bookingDisplayDtoConverter;
	
	@Autowired
	private IDtoConverter<Booking, BookingDisplayCloseDto> _bookingDisplayCloseDtoConverter;
	
	@Autowired
	private IDtoConverter<BookingCloseDto, BookingDisplayCloseDto> _bookingCloseDtoConverter;
	
	@Override
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, LocalDate date)
	{
		Duration timeout = Duration.ofSeconds(10);
		return webclient
				.get()
				.uri(_gatewayUrl + "/reservation/reservations/aircraft/" + aircraftId + "/date/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code.is5xxServerError(), clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.bodyToFlux(Booking.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
				.collectList()
				.block(timeout);
	}

	@Override
	public Booking createBooking(BookingNewDto bookingNewDto)
	{
		Duration timeout = Duration.ofSeconds(10);

		BookingCreateDto bookingDto = _bookingCreateDtoConverter.convertDtoToEntity(bookingNewDto);
		return webclient
				.post()
				.uri(_gatewayUrl + "/reservation/reservations")				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(bookingDto), BookingCreateDto.class)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("L'a??ronef/le membre est introuvable")))
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.onRawStatus(value -> value == 460, clientResponse -> 
					Mono.error(new AlreadyExistsException("Une r??servation existe d??j?? pour cet a??ronef et cette p??riode")))
				.bodyToMono(Booking.class)
				.block(timeout);
	}
	
	@Override
	public List<BookingDisplayDto> getBookings(String memberId, boolean closed)
	{
		Duration timeout = Duration.ofSeconds(10);
		LocalDateTime now = LocalDateTime.now();
		
		String endPoint = _gatewayUrl + "/reservation/reservations/member/" + memberId;
		if (closed)
		{
			endPoint += "/closed";
		}
		List<Booking> bookings = webclient
				.get()
				.uri(endPoint)				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToFlux(Booking.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
				.collectList()
				.block(timeout);
		
		List<BookingDisplayDto> displayBookings = new ArrayList<BookingDisplayDto>();
		
		if (bookings.size() <= 0) return displayBookings;
		
		Map<Long, Aircraft> aircrafts = getAllAircrafts();
		Aircraft defaultAircraft = new Aircraft();
		
		for (Booking booking : bookings)
		{
			BookingDisplayDto dto = _bookingDisplayDtoConverter.convertEntityToDto(booking);
			
			if (booking.getDepartureTime().isAfter(now)) dto.setCanCancel(true);
			
			Aircraft aircraft = aircrafts.getOrDefault(booking.getAircraftId(), defaultAircraft);
			dto.setAircraft(aircraft.getMake() + " " + aircraft.getModel() + " " + aircraft.getRegistration());
			
			displayBookings.add(dto);
		}
		
		return displayBookings;
	}
	
	private Map<Long, Aircraft> getAllAircrafts()
	{
		Duration timeout = Duration.ofSeconds(10);
		
		List<Aircraft> aircrafts = webclient
				.get()
				.uri(_gatewayUrl + "/hangar/aircrafts")				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToFlux(Aircraft.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
				.collectList()
				.block(timeout);
		
		Map<Long, Aircraft> results = new HashMap<>();
		
		if (aircrafts.size() <= 0) return results;
		
		for (Aircraft aircraft : aircrafts)
		{
			results.put(aircraft.getId(), aircraft);
		}
		
		return results;
	}

	@Override
	public void cancelBooking(long id)
	{
		Duration timeout = Duration.ofSeconds(10);

		webclient.delete()
				.uri(_gatewayUrl + "/reservation/reservations/" + id)				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("La r??servation n'existe pas")))
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service R??servation n'est pas accessible")))
				.bodyToMono(void.class)
				.block(timeout);
	}

	@Override
	public BookingDisplayCloseDto initBookingCloseDto(long id) throws EntityNotFoundException
	{
		Booking booking = getBooking(id);
					
		if (booking == null || booking.isClosed()) throw new EntityNotFoundException("Cette r??servation n'existe pas ou est clotur??e");
			
		return _bookingDisplayCloseDtoConverter.convertEntityToDto(booking);
	}
	
	private Booking getBooking(long id)
	{
		Duration timeout = Duration.ofSeconds(10);
		
		return webclient
			.get()
			.uri(_gatewayUrl + "/reservation/reservations/" + id)				
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.retrieve()
			.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("La r??servation n'existe pas")))
			.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service R??servation n'est pas accessible")))
			.bodyToMono(Booking.class)
			.block(timeout);
	}

	@Override
	public void closeBooking(String givenName, String familyName, String email, BookingDisplayCloseDto closedBooking)
	{
		Duration timeout = Duration.ofSeconds(10);
		
		BookingCloseDto bookingToClose = _bookingCloseDtoConverter.convertDtoToEntity(closedBooking);
		bookingToClose.setGivenName(givenName);
		bookingToClose.setFamilyName(familyName);
		bookingToClose.setEmail(email);
		
		webclient
			.put()
			.uri(_gatewayUrl + "/reservation/reservations/" + closedBooking.getId())				
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(Mono.just(bookingToClose), BookingCloseDto.class)
			.retrieve()
			.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("La r??servation n'existe pas")))
			.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service R??servation n'est pas accessible")))
			.bodyToMono(void.class)
			.block(timeout);
	}
}
