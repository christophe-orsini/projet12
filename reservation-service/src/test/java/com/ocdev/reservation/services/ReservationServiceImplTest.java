package com.ocdev.reservation.services;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dao.ReservationRepository;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.proxies.HangarProxy;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest
{
	private ReservationServiceImpl _systemUnderTest;
	
	private AutoCloseable _closeable;
	@Mock private ReservationRepository _reservationRepositoryMock;
	@Mock private HangarProxy _hangarProxyMock;
	
	//@Mock private IDtoConverter<Aircraft, AircraftDto> _aircraftDtoConverterMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 _systemUnderTest = new ReservationServiceImpl(_reservationRepositoryMock, _hangarProxyMock);
	}
	
	@AfterEach public void releaseMocks() throws Exception
	{
        _closeable.close();
    }
	
	@Test
	void isAircaftAvailable_ShouldReturnFalse_WhenAircraftIsNotAvailable() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setAvailable(false);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", new Date(), 1.0d);
		
		// assert
		assertThat(actual).isFalse();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
	}
	
	@Test
	void isAircaftAvailable_ShouldReturnFalse_WhenAircraftIsBooked() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		Booking booking = new Booking(1, 1, "Vol local", new Date(), 1.5d);
		List<Booking> reservations = new ArrayList<Booking>();
		reservations.add(booking);
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(reservations);
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", new Date(), 1.0d);
		
		// assert
		assertThat(actual).isFalse();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class));
	}
	
	@Test
	void isAircaftAvailable_ShouldReturnTrue_WhenAircraftIsNotBooked() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(new ArrayList<Booking>());
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", new Date(), 1.0d);
		
		// assert
		assertThat(actual).isTrue();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class));
	}
}
