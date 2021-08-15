package com.ocdev.airclub.services;

import java.time.LocalDate;
import java.util.List;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;
import com.ocdev.airclub.dto.BookingDisplayDto;
import com.ocdev.airclub.dto.BookingNewDto;
import com.ocdev.airclub.errors.EntityNotFoundException;

public interface BookingService
{
	public List<Booking> getBookingForAircraftAndDay(long aircraftId, LocalDate date);
	public Booking createBooking(BookingNewDto bookingNewDto);
	public List<BookingDisplayDto> getBookings(String memberId, boolean closed);
	public void cancelBooking(long id);
	public BookingDisplayCloseDto initBookingCloseDto(long id) throws EntityNotFoundException;
	public void closeBooking(BookingDisplayCloseDto closedBooking);
}
