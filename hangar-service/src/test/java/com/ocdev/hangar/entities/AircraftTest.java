package com.ocdev.hangar.entities;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class AircraftTest
{
	@Test
	void toString_returnsString()
	{
		// arrange 
		Aircraft entityUnderTest = new Aircraft();
		entityUnderTest.setRegistration("F-GHNY");
		entityUnderTest.setMake("Cessna");
		entityUnderTest.setModel("C152");
		String expected = "Aircraft [registration=F-GHNY, make=Cessna, model=C152]"; 
				
		// act
		String actual = entityUnderTest.toString();
		
		// assert
		assertThat(actual).isEqualTo(expected);
	}
}
