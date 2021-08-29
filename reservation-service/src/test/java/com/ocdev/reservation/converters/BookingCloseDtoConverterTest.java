package com.ocdev.reservation.converters;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCloseDto;

public class BookingCloseDtoConverterTest
{
	private IDtoConverter<Booking, BookingCloseDto> converterUnderTest;
	
	@BeforeEach
	void setUp() throws Exception
	{
		  converterUnderTest = new BookingCloseDtoConverter();
	}
	
	@Test
	void convertDtoToEntity_returnsEntity()
	{
		// arrange
		LocalDateTime departureTime= LocalDateTime.now();
		LocalDateTime arrivalTime= departureTime.plusHours(1).plusMinutes(36);
		
		BookingCloseDto dto = new BookingCloseDto();
		dto.setDescription("Dummy");
		dto.setDepartureTime(departureTime);
		dto.setDuration(1.6);
		
		Booking expected = new Booking();
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
