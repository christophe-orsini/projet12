package com.ocdev.reservation.dao;

import java.time.LocalDate;
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
	@Query(value = "select b from Booking b where aircraft_id = :acftId AND closed = 0 AND (departure_time BETWEEN :departure AND :arrival OR arrival_time BETWEEN :departure AND :arrival)")
	public List<Booking> findAllBookingForAircraftId(@Param("acftId") long aircraftId, 
			@Param("departure") LocalDateTime departureTime, @Param("arrival") LocalDateTime arrivalTime);
	
	public Collection<Booking> findAllByMemberIdAndClosed(String memberId, boolean closed);
	
	@Query(value = "select * from Booking b where aircraft_id = :acftId AND closed = 0 AND (DATE(departure_time) = :date OR DATE(arrival_time) = :date)", nativeQuery = true)
	public Collection<Booking> findAllByAircraftIdAndDate(@Param("acftId")long aircraftId, @Param("date") LocalDate date);
}
