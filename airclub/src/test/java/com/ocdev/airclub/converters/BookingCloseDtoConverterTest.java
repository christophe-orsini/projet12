package com.ocdev.airclub.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.airclub.dto.BookingCloseDto;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;

public class BookingCloseDtoConverterTest
{
	private IDtoConverter<BookingCloseDto, BookingDisplayCloseDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new BookingCloseDtoConverter();
	}
	
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDate departureDate= LocalDate.of(2021, 8, 13);
		LocalTime departureTime = LocalTime.of(16, 10, 0);
		LocalDate arrivalDate= LocalDate.of(2021, 8, 13);
		LocalTime arrivalTime = LocalTime.of(18, 5, 0);
		
		BookingDisplayCloseDto dto = new BookingDisplayCloseDto();
		dto.setId(1);
		dto.setDescription("Dummy");
		dto.setDepartureDate(departureDate);
		dto.setDepartureTime(departureTime);
		dto.setArrivalDate(arrivalDate);
		dto.setArrivalTime(arrivalTime);
		
		// act
		BookingCloseDto actual = converterUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual.getDescription()).isEqualTo("Dummy");
		assertThat(actual.getDepartureTime()).isEqualTo(LocalDateTime.of(departureDate, departureTime));
		assertThat(actual.getDuration()).isEqualTo(1.9,  within(0.1));
	}
	
	@Test
	void convertEntityToDto_raiseNotYetImplementedException()
	{
		// 	arrange
		BookingCloseDto entity = new BookingCloseDto();
	
		// act & assert
		assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() ->
		{
			converterUnderTest.convertEntityToDto(entity);
		}).withMessage("Not yet implemented!");
	}
}
