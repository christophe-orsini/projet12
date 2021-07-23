package com.ocdev.reservation.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;

public class ReservationCreateDtoConverterTest
{
	private IDtoConverter<Booking, BookingCreateDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new ReservationCreateDtoConverter();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDateTime testDate= LocalDateTime.now();
		BookingCreateDto dto = new BookingCreateDto();
		dto.setMemberId(9);
		dto.setAircraft("F-GAAA");
		dto.setDescription("Dummy");
		dto.setDepartureTime(testDate);
		dto.setDuration(1.5);
		
		Booking expected = new Booking();
		expected.setMemberId(9);
		expected.setDescription("Dummy");
		expected.setDepartureTime(testDate);
				
		// act
		Booking actual = converterUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test
	void convertDtoToEntity_raiseNotYetImplementedException()
	{
		// 	arrange
		Booking entity = new Booking();
	
		// act & assert
		assertThatExceptionOfType(NotYetImplementedException.class).isThrownBy(() ->
		{
			converterUnderTest.convertEntityToDto(entity);
		}).withMessage("Not yet implemented!");
	}
}
