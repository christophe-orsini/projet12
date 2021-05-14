package com.ocdev.hangar.converters;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;

public class AircraftDtoConverterTest
{
	private IDtoConverter<Aircraft, AircraftDto> dtoUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  dtoUnderTest = new AircraftDtoConverter();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		AircraftDto dto = new AircraftDto();
		dto.setRegistration("F-GHNY");
		dto.setMake("Cessna");
		dto.setModel("C152");
		dto.setHourlyRate(123);
		dto.setTotalTime(1234.5);
		dto.setEmptyWeight(456);
		dto.setMaxWeight(789);
		dto.setMaxPax(4);
		dto.setMaxFuel(234);
		dto.setFuelType("100LL");
		
		Aircraft expected = new Aircraft();
		expected.setRegistration("F-GHNY");
		expected.setMake("Cessna");
		expected.setModel("C152");
		expected.setHourlyRate(123);
		expected.setTotalTime(1234.5);
		expected.setEmptyWeight(456);
		expected.setMaxWeight(789);
		expected.setMaxPax(4);
		expected.setMaxFuel(234);
		expected.setFuelType("100LL");
				
		// act
		Aircraft actual = dtoUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
}
