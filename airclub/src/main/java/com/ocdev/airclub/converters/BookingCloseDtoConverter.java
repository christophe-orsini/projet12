package com.ocdev.airclub.converters;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.airclub.dto.BookingCloseDto;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;

@Component
public class BookingCloseDtoConverter implements IDtoConverter<BookingCloseDto, BookingDisplayCloseDto>
{
	@Override
	public BookingCloseDto convertDtoToEntity(BookingDisplayCloseDto dto)
	{
		BookingCloseDto entity = new BookingCloseDto();
		
		entity.setDescription(dto.getDescription());
		
		LocalDateTime departureDate = LocalDateTime.of(dto.getDepartureDate(), dto.getDepartureTime());
		entity.setDepartureTime(departureDate);
		
		LocalDateTime arrivalDate = LocalDateTime.of(dto.getArrivalDate(), dto.getArrivalTime());
		
		double duration = Duration.between(departureDate, arrivalDate).toMinutes() / 60.0;
		duration = Math.round(duration * 10.0) / 10.0;
		entity.setDuration(duration);
		
		return entity;
	}

	@Override
	public BookingDisplayCloseDto convertEntityToDto(BookingCloseDto entity)
	{
		throw new NotYetImplementedException();
	}
}