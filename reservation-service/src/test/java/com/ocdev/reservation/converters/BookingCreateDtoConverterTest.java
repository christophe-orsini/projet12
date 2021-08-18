package com.ocdev.reservation.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;

public class BookingCreateDtoConverterTest
{
	private IDtoConverter<Booking, BookingCreateDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new BookingCreateDtoConverter();
	}
	
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDateTime departureTime= LocalDateTime.now();
		LocalDateTime arrivalTime= departureTime.plusHours(1).plusMinutes(36);
		
		BookingCreateDto dto = new BookingCreateDto();
		dto.setMemberId("9");
		dto.setAircraftId(1);
		dto.setDescription("Dummy");
		dto.setDepartureTime(departureTime);
		dto.setDuration(1.6);
		
		Booking expected = new Booking();
		expected.setMemberId("9");
		expected.setDescription("Dummy");
		expected.setDepartureTime(departureTime);
		expected.setArrivalTime(arrivalTime);
				
		// act
		Booking actual = converterUnderTest.convertDtoToEntity(dto);
		
		// assert
		assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId());
		assertThat(actual.getAircraftId()).isEqualTo(expected.getAircraftId());
		assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
		assertThat(actual.getDepartureTime()).isEqualTo(expected.getDepartureTime());
		assertThat(actual.getArrivalTime()).isEqualTo(expected.getArrivalTime());
		assertThat(actual.isClosed()).isEqualTo(expected.isClosed());
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
