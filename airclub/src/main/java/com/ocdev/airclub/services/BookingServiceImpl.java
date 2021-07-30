package com.ocdev.airclub.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.converters.IDtoConverter;
import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingCreateDto;
import com.ocdev.airclub.dto.BookingNewDto;
import com.ocdev.airclub.errors.AlreadyExistsException;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ProxyException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class BookingServiceImpl implements BookingService
{
	private final String _gatewayUrl = "http://localhost:8080";
	
	@Autowired
	WebClient webclient;
	
	@Autowired
	private IDtoConverter<BookingCreateDto, BookingNewDto> _bookingCreateDtoConverter;
	
	@Override
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, LocalDate date)
	{
		Duration timeout = Duration.ofSeconds(2);
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
		Duration timeout = Duration.ofSeconds(5);
		
		BookingCreateDto bookingDto = _bookingCreateDtoConverter.convertDtoToEntity(bookingNewDto);
		return webclient
				.post()
				.uri(_gatewayUrl + "/reservation/reservations")				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(bookingDto), BookingCreateDto.class)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("L'aéronef/le membre est introuvable")))
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.onRawStatus(value -> value == 460, clientResponse -> 
					Mono.error(new AlreadyExistsException("Une réservation existe déjà pour cet aéronef et cette période")))
				.bodyToMono(Booking.class)
				.block(timeout);
	}
}
