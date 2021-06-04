package com.ocdev.reservation.services;

import java.util.Collection;
import java.util.Date;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.EntityNotFoundException;

public interface ReservationService
{
	public boolean isAircaftAvailable(String registration, Date startTime, double duration) throws EntityNotFoundException;
}
