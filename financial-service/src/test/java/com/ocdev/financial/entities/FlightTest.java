package com.ocdev.financial.entities;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class FlightTest
{
	@Test
	void toString_returnsString()
	{
		// arrange
		Calendar date = new GregorianCalendar(2021,04,20);
		Flight entityUnderTest = new Flight();
		entityUnderTest.setMemberId(5);
		entityUnderTest.setAircraft("F-GCNS CESSNA C152");
		entityUnderTest.setLineItem("Vol local Valence");
		entityUnderTest.setFlightDate(date.getTime());
		entityUnderTest.setFlightHours(2.3);
		entityUnderTest.setAircraft("F-GCNS CESSNA C152");
		entityUnderTest.setAmount(123.45);
		entityUnderTest.setPaymentDate(date.getTime());
		entityUnderTest.setClosed(true);
		
		String expected = "Flight [memberId=5, aircraft=F-GCNS CESSNA C152, lineItem=Vol local Valence, flightDate=2021-05-20, flightHours=2.3]"; 
				
		// act
		String actual = entityUnderTest.toString();
		
		// assert
		assertThat(actual).isEqualTo(expected);
	}
}
