package com.ocdev.reservation.converters;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;

@Component
public class ReservationCreateDtoConverter implements IDtoConverter<Booking, BookingCreateDto>
{
	@Override
	public Booking convertDtoToEntity(BookingCreateDto bookingDto)
	{
		Booking reservation = new Booking();
		
		reservation.setMemberId(bookingDto.getMemberId());
		reservation.setDescription(bookingDto.getDescription());
		reservation.setDepartureTime(bookingDto.getDepartureTime());
		
		return reservation;
	}

	@Override
	public BookingCreateDto convertEntityToDto(Booking entity)
	{
		throw new NotYetImplementedException();
	}
}