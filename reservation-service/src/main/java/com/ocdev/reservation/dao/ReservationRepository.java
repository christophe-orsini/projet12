package com.ocdev.reservation.dao;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ocdev.reservation.entities.Booking;

@Repository
public interface ReservationRepository extends JpaRepository<Booking, Long>
{
	@Query(value = "select b from Booking b where aircraft_id = :acftId AND closed = 0 AND (departure_time between :departure and :arrival or arrival_time between :departure and :arrival)")
	public List<Booking> findAllBookingForAircraftId(@Param("acftId") long aircraftId, 
			@Param("departure") LocalDateTime departureTime, @Param("arrival") LocalDateTime arrivalTime);
	
	public Collection<Booking> findAllByMemberIdAndClosed(long memberId, boolean closed);
	
	@Query(value = "select b from Booking b where aircraft_id = :acftId AND closed = 0 AND (DATE(departure_time) = :date OR DATE(arrival_time) = :date)")
	public Collection<Booking> findAllByAircraftIdAndDate(@Param("acftId")long aircraftId, @Param("date")Date date);
}
