package com.ocdev.reservation.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ocdev.reservation.entities.Booking;

@Repository
public interface ReservationRepository extends JpaRepository<Booking, Long>
{
	@Query(value = "SELECT * FROM booking WHERE aircraft_id = ?1 AND departure_time >= ?2 AND arrival_time <= ?3 AND closed = 0", nativeQuery = true)
	public List<Booking> findAllBookingForAircraftId(long aircraftId, Date departureTime, Date arrivalTime);
}
