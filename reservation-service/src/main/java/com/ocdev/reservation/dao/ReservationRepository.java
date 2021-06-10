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
	@Query(value = "select b from Booking b where aircraft_id = :acftId AND closed = 0 AND (departure_time between :departure and :arrival or arrival_time between :departure and :arrival)")
	public List<Booking> findAllBookingForAircraftId(@Param("acftId") long aircraftId, @Param("departure") Date departureTime, @Param("arrival") Date arrivalTime);
}
