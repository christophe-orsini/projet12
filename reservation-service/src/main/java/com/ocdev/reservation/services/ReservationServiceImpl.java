package com.ocdev.reservation.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dao.ReservationRepository;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.proxies.HangarProxy;

@Service
public class ReservationServiceImpl implements ReservationService
{
	private ReservationRepository _reservationRepository;
	private HangarProxy _hangarProxy;
	
	public ReservationServiceImpl(@Autowired ReservationRepository repository, @Autowired HangarProxy hangarProxy)
	{
		_reservationRepository = repository;
		_hangarProxy = hangarProxy;
	}

	@Override
	public boolean isAircaftAvailable(String registration, Date startTime, double duration) throws EntityNotFoundException
	{
		Aircraft aircraft = _hangarProxy.getAircraft(registration);
		if (!aircraft.isAvailable()) return false;
		
		int hours = (int)duration;
		int minutes = (int)((duration - hours) * 60);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
		Date arrivalTime = calendar.getTime();
		Collection<Booking> reservations = _reservationRepository.findAllBookingForAircraftId(aircraft.getId(), startTime, arrivalTime);
		
		return reservations.size() == 0;
	}
}
