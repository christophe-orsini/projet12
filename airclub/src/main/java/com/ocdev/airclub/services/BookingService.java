package com.ocdev.airclub.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingNewDto;

public interface BookingService
{
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, Date date);
	public Optional<Booking> createBooking(BookingNewDto bookingNewDto);
}
