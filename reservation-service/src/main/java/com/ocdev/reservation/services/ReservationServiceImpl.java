package com.ocdev.reservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocdev.reservation.dao.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService
{
	private ReservationRepository _reservationRepository;
	
	public ReservationServiceImpl(@Autowired ReservationRepository repository)
	{
		_reservationRepository = repository;
	}
}
