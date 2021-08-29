package com.ocdev.financial.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.entities.Flight;

public class FlightRecordDtoConverterTest
{
	private IDtoConverter<Flight, FlightRecordDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new FlightRecordDtoConverter();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDate testDate = LocalDate.now();
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId("9");
		dto.setAircraft("F-GAAA");
		dto.setLineItem("Dummy");
		dto.setFlightDate(testDate);
		dto.setFlightHours(1.5);
		dto.setAmount(123.4);
		
		Flight expected = new Flight();
		expected.setMemberId("9");
		expected.setAircraft("F-GAAA");
		expected.setLineItem("Dummy");
		expected.setFlightDate(testDate);
		expected.setFlightHours(1.5);
		expected.setAmount(123.4);
				
		// act
		Flight actual = converterUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test
	void convertDtoToEntity_raiseNotYetImplementedException()
	{
		// 	arrange
		Flight entity = new Flight();
	
		// act & assert
		assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() ->
		{
			converterUnderTest.convertEntityToDto(entity);
		}).withMessage("Not yet implemented!");
	}
}
