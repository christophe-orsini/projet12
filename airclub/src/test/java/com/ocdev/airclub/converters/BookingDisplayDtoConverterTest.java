package com.ocdev.airclub.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingDisplayDto;

public class BookingDisplayDtoConverterTest
{
	private IDtoConverter<Booking, BookingDisplayDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new BookingDisplayDtoConverter();
	}
	
	@Test
	void convertDtoToEntity_raiseNotYetImplementedException()
	{
		// 	arrange
		BookingDisplayDto dto = new BookingDisplayDto();
	
		// act & assert
		assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() ->
		{
			converterUnderTest.convertDtoToEntity(dto);
		}).withMessage("Not yet implemented!");
	}
	
	@Test
	void convertEntityToDto_returnsDto()
	{
		// arrange
		LocalDate departureDate= LocalDate.of(2021, 8, 13);
		LocalTime departureTime = LocalTime.of(16, 01, 0);
		LocalDate arrivalDate= LocalDate.of(2021, 8, 13);
		LocalTime arrivalTime = LocalTime.of(18, 0, 0);
		
		Booking entity = new Booking();
		entity.setId(7);
		entity.setMemberId("9");
		entity.setAircraftId(1);
		entity.setDescription("Dummy");
		entity.setDepartureTime(LocalDateTime.of(departureDate, departureTime));
		entity.setArrivalTime(LocalDateTime.of(arrivalDate, arrivalTime));
		entity.setClosed(true);
		
		// act
		BookingDisplayDto actual = converterUnderTest.convertEntityToDto(entity);
		
		// assert
		assertThat(actual.getId()).isEqualTo(7);
		assertThat(actual.getDescription()).isEqualTo("Dummy");
		assertThat(actual.getDepartureTime()).isEqualTo(LocalDateTime.of(departureDate, departureTime));
		assertThat(actual.getArrivalTime()).isEqualTo(LocalDateTime.of(arrivalDate, arrivalTime));
		assertThat(actual.isClosed()).isTrue();
		assertThat(actual.isCanCancel()).isFalse();
	}
}
}
