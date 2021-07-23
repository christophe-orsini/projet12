package com.ocdev.reservation.entities;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


public class BookingTest
{
	@Test
	void toString_returnsString()
	{
		// arrange
		LocalDateTime  date = LocalDateTime.of(2021, 6, 15, 10, 30, 00);
		Booking entityUnderTest = new Booking();
		entityUnderTest.setMemberId(9);
		entityUnderTest.setAircraftId(8);
		entityUnderTest.setDescription("Dummy");
		entityUnderTest.setDepartureTime(date);
		entityUnderTest.setArrivalTime(date);
		String expected = "Booking [memberId=9, aircraftId=8, description=Dummy, departureTime=2021-06-15 10:30, arrivalTime=2021-06-15 10:30]"; 
				
		// act
		String actual = entityUnderTest.toString();
		
		// assert
		assertThat(actual).isEqualTo(expected);
	}
}
