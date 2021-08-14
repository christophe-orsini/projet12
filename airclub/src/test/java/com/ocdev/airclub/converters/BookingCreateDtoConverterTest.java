package com.ocdev.airclub.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.airclub.dto.BookingCreateDto;
import com.ocdev.airclub.dto.BookingNewDto;

public class BookingCreateDtoConverterTest
{
	private IDtoConverter<BookingCreateDto, BookingNewDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new BookingCreateDtoConverter();
	}
	
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDate departureDate= LocalDate.of(2021, 8, 13);
		LocalTime departureTime = LocalTime.of(16, 01, 0);
		LocalDate arrivalDate= LocalDate.of(2021, 8, 13);
		LocalTime arrivalTime = LocalTime.of(18, 0, 0);
		
		BookingNewDto dto = new BookingNewDto();
		dto.setMemberId("9");
		dto.setAircraftId(1);
		dto.setDescription("Dummy");
		dto.setDepartureDate(departureDate);
		dto.setDepartureTime(departureTime);
		dto.setArrivalDate(arrivalDate);
		dto.setArrivalTime(arrivalTime);
		
		// act
		BookingCreateDto actual = converterUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual.getMemberId()).isEqualTo("9");
		assertThat(actual.getAircraftId()).isEqualTo(1);
		assertThat(actual.getDescription()).isEqualTo("Dummy");
		assertThat(actual.getDepartureTime()).isEqualTo(LocalDateTime.of(departureDate, departureTime));
		assertThat(actual.getDuration()).isEqualTo(1.98,  within(0.016));
	}
	
	@Test
	void convertEntityToDto_raiseNotYetImplementedException()
	{
		// 	arrange
		BookingCreateDto entity = new BookingCreateDto();
	
		// act & assert
		assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() ->
		{
			converterUnderTest.convertEntityToDto(entity);
		}).withMessage("Not yet implemented!");
	}
}
