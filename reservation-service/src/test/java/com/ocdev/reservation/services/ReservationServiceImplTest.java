package com.ocdev.reservation.services;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.converters.IDtoConverter;
import com.ocdev.reservation.dao.ReservationRepository;
import com.ocdev.reservation.dto.BookingCloseDto;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.AlreadyExistsException;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.errors.ProxyException;
import com.ocdev.reservation.messages.AircraftTotalTimeMessage;
import com.ocdev.reservation.messages.RegisterFlightMessage;
import com.ocdev.reservation.proxies.HangarProxy;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest
{
	private ReservationServiceImpl _systemUnderTest;
	
	private AutoCloseable _closeable;
	@Mock private ReservationRepository _reservationRepositoryMock;
	@Mock private HangarProxy _hangarProxyMock;
	@Mock private IDtoConverter<Booking, BookingCreateDto> _bookingDtoConverterMock;
	@Mock private AmqpTemplate _rabbitTemplateMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 _systemUnderTest = new ReservationServiceImpl(
				 _reservationRepositoryMock,
				 _hangarProxyMock, 
				 _bookingDtoConverterMock,
				 _rabbitTemplateMock);
	}
	
	@AfterEach public void releaseMocks() throws Exception
	{
        _closeable.close();
    }
	
	@Test
	void isAircaftAvailable_ShouldReturnFalse_WhenAircraftIsNotAvailable() throws EntityNotFoundException, ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Aircraft aircraft = new Aircraft();
		aircraft.setAvailable(false);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", today, 1.0d);
		
		// assert
		assertThat(actual).isFalse();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
	}
	
	@Test
	void isAircaftAvailable_ShouldReturnFalse_WhenAircraftIsBooked() throws EntityNotFoundException, ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		Booking booking = new Booking(1, 1, "Vol local", today, 1.5d);
		List<Booking> reservations = new ArrayList<Booking>();
		reservations.add(booking);
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(reservations);
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", today, 1.0d);
		
		// assert
		assertThat(actual).isFalse();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));
	}
	
	@Test
	void isAircaftAvailable_ShouldReturnTrue_WhenAircraftIsNotBooked() throws EntityNotFoundException, ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(new ArrayList<Booking>());
		
		// act
		boolean actual = _systemUnderTest.isAircaftAvailable("F-GAAA", today, 1.0d);
		
		// assert
		assertThat(actual).isTrue();
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircraft("F-GAAA");
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));
	}
	
	@Test
	void availableAircrafts_ShouldReturnEmptyList_WhenNoAircraftsAvailable() throws ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Collection<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Mockito.when(_hangarProxyMock.getAircrafts()).thenReturn(aircrafts);
		
		// act
		Collection<Aircraft> actuals = _systemUnderTest.availableAircrafts(today, 1.0d);
		
		// assert
		assertThat(actuals.isEmpty()).isTrue();
	
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircrafts();
	}
	
	@Test
	void availableAircrafts_ShouldReturnEmpty_WhenAircraftIsBooked() throws ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Collection<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1L);
		aircraft.setRegistration("F-GAAA");
		aircraft.setAvailable(true);
		aircrafts.add(aircraft);
		Mockito.when(_hangarProxyMock.getAircrafts()).thenReturn(aircrafts);
		
		Booking booking = new Booking(1, 1, "Vol local", today, 1.5d);
		List<Booking> reservations = new ArrayList<Booking>();
		reservations.add(booking);
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(reservations);
		
		// act
		Collection<Aircraft> actuals = _systemUnderTest.availableAircrafts(today, 1.0d);
		
		// assert
		assertThat(actuals).size().isEqualTo(0);
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircrafts();
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));
	}
	
	@Test
	void availableAircrafts_ShouldReturnOne_WhenAircraftIsAvailableAndNotBooked() throws ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Collection<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Aircraft aircraft1 = new Aircraft();
		aircraft1.setId(1L);
		aircraft1.setRegistration("F-GAAA");
		aircraft1.setAvailable(true);
		aircrafts.add(aircraft1);
		Aircraft aircraft2 = new Aircraft();
		aircraft2.setId(2L);
		aircraft2.setRegistration("F-GBBB");
		aircraft2.setAvailable(true);
		aircrafts.add(aircraft2);
		Mockito.when(_hangarProxyMock.getAircrafts()).thenReturn(aircrafts);
		
		Booking booking = new Booking(1, 1, "Vol local", today, 1.5d);
		List<Booking> reservations = new ArrayList<Booking>();
		reservations.add(booking);
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
			Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
			.thenReturn(reservations).thenReturn(new ArrayList<Booking>());
		
		// act
		Collection<Aircraft> actuals = _systemUnderTest.availableAircrafts(today, 1.0d);
		
		// assert
		assertThat(actuals).size().isEqualTo(1);
		Mockito.verify(_hangarProxyMock, Mockito.times(1)).getAircrafts();
		Mockito.verify(_reservationRepositoryMock, Mockito.times(2)).
			findAllBookingForAircraftId(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class));
	}
	
	@Test
	public void createBooking_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		BookingCreateDto bookingCreateDto= new BookingCreateDto();
		bookingCreateDto.setMemberId(-1);
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.createBooking(bookingCreateDto);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	public void createBooking_ShouldRaiseEntityNotFoundException_WhenAircraftNotExists() throws ProxyException
	{
		//arrange
		BookingCreateDto bookingCreateDto= new BookingCreateDto();
		bookingCreateDto.setMemberId(1);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenThrow(ProxyException.class);
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.createBooking(bookingCreateDto);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	public void createBooking_ShouldRaiseAlreadyExistsException_WhenAircraftNotAvailable() throws ProxyException
	{
		//arrange
		LocalDateTime testDate = LocalDateTime.of(2021, 5, 15, 10, 30);
		BookingCreateDto bookingCreateDto= new BookingCreateDto(1, "F-GAAA", "Dummy", testDate, 1.5);
		Aircraft aircraft = new Aircraft();
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		
		// act & assert
		assertThatExceptionOfType(AlreadyExistsException.class).isThrownBy(() ->
		{
			_systemUnderTest.createBooking(bookingCreateDto);
		}).withMessage("Cet aéronef est déjà réservé pour la période demandée");
	}
	
	@Test
	public void createBooking_ShouldReturnBooking_WhenAllOK() throws ProxyException, EntityNotFoundException, AlreadyExistsException
	{
		//arrange
		LocalDateTime testDate = LocalDateTime.of(2021, 5, 15, 10, 30);
		BookingCreateDto bookingCreateDto= new BookingCreateDto(9, "F-GAAA", "Dummy", testDate, 1.5);
		
		Aircraft aircraft = new Aircraft();
		aircraft.setId(8);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraft(Mockito.anyString())).thenReturn(aircraft);
		Mockito.when(_reservationRepositoryMock.findAllBookingForAircraftId(
				Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(new ArrayList<Booking>());
		
		Booking booking = new Booking();
		booking.setMemberId(9);
		booking.setDescription("Dummy");
		booking.setDepartureTime(testDate);
		booking.setArrivalTime(testDate.plusHours(1).plusMinutes(30));
		Mockito.when(_bookingDtoConverterMock.convertDtoToEntity(Mockito.any(BookingCreateDto.class))).thenReturn(booking);
		Mockito.when(_reservationRepositoryMock.save(Mockito.any(Booking.class))).thenReturn(booking);
		
		LocalDateTime arrivalTime = testDate.plusHours(1).plusMinutes(30);
		
		// act
		Booking actual = _systemUnderTest.createBooking(bookingCreateDto);
		
		// assert
		assertThat(actual.getArrivalTime()).isEqualTo(arrivalTime);
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).save(Mockito.any(Booking.class));
	}
	
	@Test
	public void getAllBookings_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.getAllBookings(-1);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	public void getAllBookings_ShouldReturnsEmpty_WhenNoReservations() throws EntityNotFoundException
	{
		//arrange
		Mockito.when(_reservationRepositoryMock.findAllByMemberIdAndClosed(Mockito.anyLong(), Mockito.anyBoolean())).
			thenReturn(new ArrayList<Booking>());
		
		// act
		Collection<Booking> actual = _systemUnderTest.getAllBookings(9);
		
		// assert
		assertThat(actual).size().isEqualTo(0);
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).findAllByMemberIdAndClosed(9, false);
	}
	
	@Test
	public void getAllBookings_ShouldReturnsList_WhenReservationsExist() throws EntityNotFoundException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Collection<Booking> reservations = new ArrayList<Booking>();
		Booking booking = new Booking(9, 1, "Dummy", today, 1.5);
		reservations.add(booking);
		Mockito.when(_reservationRepositoryMock.findAllByMemberIdAndClosed(Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(reservations);
		
		// act
		Collection<Booking> actual = _systemUnderTest.getAllBookings(9);
		
		// assert
		assertThat(actual).size().isEqualTo(1);
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).findAllByMemberIdAndClosed(9, false);
	}
	
	@Test
	public void deleteBooking_ShouldRaiseEntityNotFoundException_WhenReservationNotExists()
	{
		//arrange
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.deleteBooking(999);
		}).withMessage("Cette réservation n'existe pas");
	}
	
	@Test
	public void deleteBooking_ShouldRaiseEntityNotFoundException_WhenReservationIsClosed()
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Booking reservation = new Booking(9, 1, "Dummy", today, 1.5);
		reservation.setClosed(true);
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(reservation));
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.deleteBooking(9);
		}).withMessage("Cette réservation est clôturée");
	}
	
	@Test
	public void deleteBooking_ShouldSuccess_WhenReservationIsActive() throws EntityNotFoundException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		Booking reservation = new Booking(9, 1, "Dummy", today, 1.5);
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(reservation));
		Mockito.doNothing().when(_reservationRepositoryMock).delete(Mockito.any(Booking.class));
		
		// act
		_systemUnderTest.deleteBooking(9);
		
		// assert
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).delete(reservation);
	}
	
	@Test
	public void closeBooking_ShouldRaiseEntityNotFoundException_WhenReservationNotExists()
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		BookingCloseDto bookingCloseDto = new BookingCloseDto("Dummy", today, 1.5);
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.closeBooking(9, bookingCloseDto);
		}).withMessage("Cette réservation n'existe pas");
	}
	
	@Test
	public void closeBooking_ShouldRaiseEntityNotFoundException_WhenReservationIsClosed()
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		BookingCloseDto bookingCloseDto = new BookingCloseDto("Dummy", today, 1.5); 
		
		Booking reservation = new Booking(9, 1, "Dummy", today, 1.5);
		reservation.setClosed(true);
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(reservation));
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			_systemUnderTest.closeBooking(9, bookingCloseDto);
		}).withMessage("Cette réservation est clôturée");
	}
	
	@Test
	public void closeBooking_ShouldReturnReservation_WhenAllOK() throws EntityNotFoundException, ProxyException
	{
		//arrange
		LocalDateTime today = LocalDateTime.now();
		BookingCloseDto bookingCloseDto = new BookingCloseDto("Dummy", today, 1.3);
		
		Booking reservation = new Booking(9, 1, "Dummy", today, 1.5);
		Mockito.when(_reservationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(reservation));
		
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1);
		aircraft.setAvailable(true);
		Mockito.when(_hangarProxyMock.getAircraftById(Mockito.anyLong())).thenReturn(aircraft);
		
		Mockito.when(_reservationRepositoryMock.save(Mockito.any(Booking.class))).thenReturn(new Booking());
		Mockito.doNothing().when(_rabbitTemplateMock)
			.convertAndSend(Mockito.any(), Mockito.any(), Mockito.any(AircraftTotalTimeMessage.class));
		Mockito.doNothing().when(_rabbitTemplateMock)
		.convertAndSend(Mockito.any(), Mockito.any(), Mockito.any(RegisterFlightMessage.class));
		
		// act
		_systemUnderTest.closeBooking(9, bookingCloseDto);
		
		// assert
		Mockito.verify(_reservationRepositoryMock, Mockito.times(1)).save(Mockito.any(Booking.class));
	}
}
