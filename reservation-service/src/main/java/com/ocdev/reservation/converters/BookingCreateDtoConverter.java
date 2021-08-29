package com.ocdev.reservation.converters;

import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;

@Component
public class BookingCreateDtoConverter implements IDtoConverter<Booking, BookingCreateDto>
{
	@Override
	public Booking convertDtoToEntity(BookingCreateDto dto)
	{
		Booking entity = new Booking();
		
		entity.setMemberId(dto.getMemberId());
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
	public BookingCreateDto convertEntityToDto(Booking entity)
	{
		throw new NotYetImplementedException();
	}
}