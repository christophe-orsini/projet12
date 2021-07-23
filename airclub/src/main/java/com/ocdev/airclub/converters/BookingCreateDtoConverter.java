package com.ocdev.airclub.converters;

import java.time.LocalDateTime;
import java.util.Calendar;

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
		
		Calendar departureDate = Calendar.getInstance();
		Calendar departureTime = Calendar.getInstance();
		Calendar departureDateTime = Calendar.getInstance();
		
		departureDate.setTime(bookingNewDto.getDepartureDate());
		departureTime.setTime(bookingNewDto.getDepartureTime());
		LocalDateTime departure = LocalDateTime.of(
				departureDate.get(Calendar.YEAR),
				departureDate.get(Calendar.MONTH),
				departureDate.get(Calendar.DAY_OF_MONTH),
				departureTime.get(Calendar.HOUR),
				departureTime.get(Calendar.MINUTE)
				);
		
		departureDateTime.set(
				departureDate.get(Calendar.YEAR),
				departureDate.get(Calendar.MONTH),
				departureDate.get(Calendar.DAY_OF_MONTH),
				departureTime.get(Calendar.HOUR),
				departureTime.get(Calendar.MINUTE)
				);
		booking.setDepartureTime(departureDateTime.getTime());
		
		departureDateTime.
		date.setTime(bookingNewDto.getArrivalDate());
		time.setTime(bookingNewDto.getArrivalTime());
		dateTime.set(
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DAY_OF_MONTH),
				time.get(Calendar.HOUR),
				time.get(Calendar.MINUTE)
				);
		booking.setArrivalTime(dateTime.getTime());
		
		return booking;
	}

	@Override
	public BookingNewDto convertEntityToDto(BookingCreateDto entity)
	{
		throw new NotYetImplementedException();
	}
}