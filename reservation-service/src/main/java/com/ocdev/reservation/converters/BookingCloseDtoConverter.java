package com.ocdev.reservation.converters;

import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCloseDto;


@Component
public class BookingCloseDtoConverter implements IDtoConverter<Booking, BookingCloseDto>
{
	@Override
	public Booking convertDtoToEntity(BookingCloseDto dto)
	{
		Booking entity = new Booking();
		
		entity.setDescription(dto.getDescription());
		
		int hours = (int)dto.getDuration();
		int minutes = (int)((dto.getDuration() - hours) * 60);
		LocalDateTime departureTime = dto.getDepartureTime();
		LocalDateTime arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);
		
		entity.setDepartureTime(departureTime);
		entity.setArrivalTime(arrivalTime);
		
		return entity;
	}

	@Override
	public BookingCloseDto convertEntityToDto(Booking entity)
	{
		throw new NotYetImplementedException();
	}
}