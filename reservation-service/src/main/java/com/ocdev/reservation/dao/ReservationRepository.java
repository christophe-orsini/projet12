package com.ocdev.reservation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocdev.reservation.entities.Booking;

@Repository
public interface ReservationRepository extends JpaRepository<Booking, Long>
{
	
}
