package com.ocdev.airclub.services;

import java.time.LocalDate;
import java.util.List;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingNewDto;

public interface BookingService
{
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, LocalDate date);
	public Booking createBooking(BookingNewDto bookingNewDto);
}
