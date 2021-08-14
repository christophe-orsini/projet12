package com.ocdev.airclub.converters;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;

@Component
public class BookingDisplayCloseDtoConverter implements IDtoConverter<Booking, BookingDisplayCloseDto>
{
	@Override
	public Booking convertDtoToEntity(BookingDisplayCloseDto dto)
	{
		throw new NotYetImplementedException();
	}

	@Override
	public BookingDisplayCloseDto convertEntityToDto(Booking entity)
	{
		BookingDisplayCloseDto dto = new BookingDisplayCloseDto();
		
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setDepartureDate(entity.getDepartureTime().toLocalDate());
		dto.setDepartureTime(entity.getDepartureTime().toLocalTime());
		dto.setArrivalDate(entity.getArrivalTime().toLocalDate());
		dto.setArrivalTime(entity.getArrivalTime().toLocalTime());
		
		return dto;
	}
}