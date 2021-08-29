package com.ocdev.airclub.converters;


import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingDisplayDto;

@Component
public class BookingDisplayDtoConverter implements IDtoConverter<Booking, BookingDisplayDto>
{
	@Override
	public Booking convertDtoToEntity(BookingDisplayDto dto)
	{
		throw new NotYetImplementedException();
	}

	@Override
	public BookingDisplayDto convertEntityToDto(Booking entity)
	{
		BookingDisplayDto dto = new BookingDisplayDto();
		
		dto.setId(entity.getId());
		dto.setMemberId(entity.getMemberId());
		dto.setDescription(entity.getDescription());
		dto.setDepartureTime(entity.getDepartureTime());
		dto.setArrivalTime(entity.getArrivalTime());
		dto.setClosed(entity.isClosed());
		return dto;
	}
}