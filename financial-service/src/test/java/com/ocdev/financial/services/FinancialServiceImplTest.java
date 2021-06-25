package com.ocdev.financial.services;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ocdev.financial.beans.Aircraft;
import com.ocdev.financial.converters.IDtoConverter;
import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;
import com.ocdev.financial.errors.ProxyException;
import com.ocdev.financial.messages.RegisterFlightMessage;
import com.ocdev.financial.proxies.HangarProxy;

@ExtendWith(MockitoExtension.class)
public class FinancialServiceImplTest
{
	private FinancialServiceImpl systemUnderTest;
	
	private AutoCloseable _closeable;
	@Mock private FlightRepository _flightRepositoryMock;
	@Mock private SubscriptionRepository _subscriptionRepositoryMock;
	@Mock private IDtoConverter<Flight, FlightRecordDto> _flightDtoConverterMock;
	@Mock private HangarProxy _hangarProxyMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 systemUnderTest = new FinancialServiceImpl(_flightRepositoryMock, _subscriptionRepositoryMock, _flightDtoConverterMock, _hangarProxyMock);
	}
	
	@AfterEach public void releaseMocks() throws Exception
	{
        _closeable.close();
    }
	
	@Test
	void recordSubscription_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		// TODO refactor when User defined
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId(-1);
		dto.setAmount(-1);
			
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.recordSubscription(dto);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	void recordSubscription_ShouldRaiseAlreadyExistsFoundException_WhenSubsciptionIsValid()
	{
		//arrange
		// TODO refactor when User defined
		Calendar date = new GregorianCalendar(2021,11,31);
		Subscription subscription = new Subscription(9, date.getTime(), 123.45);
		subscription.setValidityDate(date.getTime());
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyLong())).thenReturn(Optional.of(subscription));
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId(9);
		dto.setAmount(-1);
		
		// act & assert
		assertThatExceptionOfType(AlreadyExistsException.class).isThrownBy(() ->
		{
			systemUnderTest.recordSubscription(dto);
		}).withMessage("Ce membre a déjà une cotisation valide");
	}
	
	@Test
	void recordSubscription_ShouldReturnsAmount_WhenAmountExists() throws EntityNotFoundException, AlreadyExistsException
	{
		//arrange
		// TODO refactor when User defined
		Calendar today = Calendar.getInstance();
		Subscription subscription = new Subscription(9, today.getTime(), 123.45);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyLong())).thenReturn(Optional.of(subscription));
		Mockito.when(_subscriptionRepositoryMock.save(Mockito.any(Subscription.class))).thenReturn(subscription);
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId(9);
		dto.setAmount(123.45);
		
		// act
		systemUnderTest.recordSubscription(dto);
		
		// assert
		Mockito.verify(_subscriptionRepositoryMock, Mockito.times(1)).save(Mockito.any(Subscription.class));
	}
	
	@Test
	void recordSubscription_ShouldReturnsZero_WhenAmountNotExists() throws EntityNotFoundException, AlreadyExistsException
	{
		//arrange
		// TODO refactor when User defined
		Calendar today = Calendar.getInstance();
		Subscription subscription = new Subscription(9, today.getTime(), 123.45);
		subscription.setId(99);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyLong())).thenReturn(Optional.of(subscription));
		
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId(9);
		Mockito.when(_subscriptionRepositoryMock.save(Mockito.any(Subscription.class))).thenReturn(subscription);
		
		// act
		systemUnderTest.recordSubscription(dto);
		
		// assert
		Mockito.verify(_subscriptionRepositoryMock, Mockito.times(1)).save(Mockito.any(Subscription.class));
	}
	
	@Test
	void recordFlight_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		// TODO refactor when User defined
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId(-1);
			
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.recordFlight(dto);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	void recordFlight_ShouldSuccess_WhenOK() throws EntityNotFoundException
	{
		//arrange
		// TODO refactor when User and Hangar endpoint defined
		Calendar today = Calendar.getInstance();
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId(9);
		dto.setAircraft("F-GCNS CESSNA C152");
		dto.setLineItem("Test");
		dto.setFlightDate(today.getTime());
		dto.setFlightHours(2.0);
		dto.setAmount(258.0);
		Flight flight = new Flight(9, "Test", today.getTime(), 2.0);
		flight.setAmount(258.0);
		Mockito.when(_flightDtoConverterMock.convertDtoToEntity(Mockito.any(FlightRecordDto.class))).thenReturn(flight);
		Mockito.when(_flightRepositoryMock.save(Mockito.any(Flight.class))).thenReturn(flight);
		
		// act
		Flight actual = systemUnderTest.recordFlight(dto);
		
		// assert
		assertThat(actual.getAmount()).isEqualTo(258.0);
		Mockito.verify(_flightRepositoryMock, Mockito.times(1)).save(Mockito.any(Flight.class));
	}
	
	@Test
	void registerEndedFlight_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		// TODO refactor when User defined
		RegisterFlightMessage message = new RegisterFlightMessage();
		message.setMemberId(-1);
			
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.registerEndedFlight(message);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	void registerEndedFlight_ShouldRaiseProxyException_WhenHangarNotAvailable() throws ProxyException
	{
		//arrange
		// TODO refactor when User defined
		RegisterFlightMessage message = new RegisterFlightMessage();
		message.setMemberId(9);
		message.setAircraftId(8);
		Mockito.when(_hangarProxyMock.getAircraftById(Mockito.anyLong())).thenThrow(ProxyException.class);
		
		// act & assert
		assertThatExceptionOfType(ProxyException.class).isThrownBy(() ->
		{
			systemUnderTest.registerEndedFlight(message);
		});
	}
	
	@Test
	void registerEndedFlight_ShouldSuccess_WhenOK() throws EntityNotFoundException, ProxyException
	{
		//arrange
		// TODO refactor when User and Hangar endpoint defined
		Calendar now = Calendar.getInstance();
		RegisterFlightMessage message = new RegisterFlightMessage();
		message.setMemberId(9);
		message.setAircraftId(8);
		Aircraft aircraft = new Aircraft();
		Mockito.when(_hangarProxyMock.getAircraftById(Mockito.anyLong())).thenReturn(aircraft);
		
		Flight flight = new Flight(9, "", now.getTime(), 1.5);
		Mockito.when(_flightRepositoryMock.save(Mockito.any(Flight.class))).thenReturn(flight);
		
		// act
		systemUnderTest.registerEndedFlight(message);
		
		// assert
		Mockito.verify(_flightRepositoryMock, Mockito.times(1)).save(Mockito.any(Flight.class));
	}
}
