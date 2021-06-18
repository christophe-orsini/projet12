package com.ocdev.reservation.entities;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;


public class BookingTest
{
	@Test
	void toString_returnsString()
	{
		// arrange
		Calendar calendar = new GregorianCalendar(2021, 5, 15, 10, 30, 00);
		Booking entityUnderTest = new Booking();
		entityUnderTest.setMemberId(9);
		entityUnderTest.setAircraftId(8);
		entityUnderTest.setDescription("Dummy");
		entityUnderTest.setDepartureTime(calendar.getTime());
		entityUnderTest.setArrivalTime(calendar.getTime());
		String expected = "Booking [memberId=9, aircraftId=8, description=Dummy, departureTime=2021-06-15 10:30, arrivalTime=2021-06-15 10:30]"; 
				
		// act
		String actual = entityUnderTest.toString();
		
		// assert
		assertThat(actual).isEqualTo(expected);
	}
}
