package com.ocdev.airclub.services;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.converters.IDtoConverter;
import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingCreateDto;
import com.ocdev.airclub.dto.BookingNewDto;

import reactor.core.publisher.Mono;

@Service
public class BookingServiceImpl implements BookingService
{
	private final String _gatewayUrl = "http://localhost:8080";
	
	@Autowired
	WebClient webclient;
	
	@Autowired
	private IDtoConverter<BookingCreateDto, BookingNewDto> _bookingCreateDtoConverter;
	
	@Override
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return webclient
				.method(HttpMethod.GET)
				.uri(_gatewayUrl + "/reservation/reservations/aircraft/" + aircraftId + "/date/" + sdf.format(date))				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToFlux(Booking.class)
				.collectList().block();
	}

	@Override
	public Optional<Booking> createBooking(BookingNewDto bookingNewDto)
	{
		Duration timeoutIn10Seconds = Duration.ofSeconds(10);
		
		BookingCreateDto bookingDto = _bookingCreateDtoConverter.convertDtoToEntity(bookingNewDto);
		return webclient
				.method(HttpMethod.POST)
				.uri(_gatewayUrl + "/reservation/reservations")				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(bookingDto), BookingCreateDto.class)
				.retrieve()
				.bodyToMono(Booking.class)
				.blockOptional(timeoutIn10Seconds);
	}
}
