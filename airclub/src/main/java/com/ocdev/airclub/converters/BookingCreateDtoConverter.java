package com.ocdev.airclub.converters;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.airclub.dto.BookingCreateDto;
import com.ocdev.airclub.dto.BookingNewDto;

@Component
public class BookingCreateDtoConverter implements IDtoConverter<BookingCreateDto, BookingNewDto>
{
	@Override
	public BookingCreateDto convertDtoToEntity(BookingNewDto bookingNewDto)
	{
		BookingCreateDto booking = new BookingCreateDto();
		
		booking.setMemberId(bookingNewDto.getMemberId());
		booking.setAircraftId(bookingNewDto.getAircraftId());
		booking.setDescription(bookingNewDto.getDescription());
		
		LocalDateTime departureDate = LocalDateTime.of(bookingNewDto.getDepartureDate(), bookingNewDto.getDepartureTime());
		booking.setDepartureTime(departureDate);
		
		LocalDateTime arrivalDate = LocalDateTime.of(bookingNewDto.getArrivalDate(), bookingNewDto.getArrivalTime());
		
		booking.setDuration(Duration.between(departureDate, arrivalDate).toMinutes() / 60.0);
		
		return booking;
	}

	@Override
	public BookingNewDto convertEntityToDto(BookingCreateDto entity)
	{
		throw new NotYetImplementedException();
	}
}