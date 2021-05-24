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

import com.ocdev.financial.converters.IDtoConverter;
import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FinancialServiceImplTest
{
	private FinancialServiceImpl systemUnderTest;
	
	private AutoCloseable _closeable;
	@Mock private FlightRepository _flightRepositoryMock;
	@Mock private SubscriptionRepository _subscriptionRepositoryMock;
	@Mock private IDtoConverter<Flight, FlightRecordDto> _flightDtoConverterMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 systemUnderTest = new FinancialServiceImpl(_flightRepositoryMock, _subscriptionRepositoryMock, _flightDtoConverterMock);
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
		Subscription actual = systemUnderTest.recordSubscription(dto);
		
		// assert
		assertThat(actual.getAmount()).isEqualTo(123.45);
		assertThat(actual.getValidityDate()).hasDayOfMonth(31);
		assertThat(actual.getValidityDate()).hasMonth(12);
		Mockito.verify(_subscriptionRepositoryMock, Mockito.times(1)).save(Mockito.any(Subscription.class));
	}
	
	@Test
	void recordSubscription_ShouldReturnsZero_WhenAmountNotExists() throws EntityNotFoundException, AlreadyExistsException
	{
		//arrange
		// TODO refactor when User defined
		Calendar today = Calendar.getInstance();
		Subscription subscription = new Subscription(9, today.getTime(), 123.45);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyLong())).thenReturn(Optional.of(subscription));
		Mockito.when(_subscriptionRepositoryMock.save(Mockito.any(Subscription.class))).thenReturn(subscription);
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId(9);
		
		// act
		Subscription actual = systemUnderTest.recordSubscription(dto);
		
		// assert
		assertThat(actual.getAmount()).isEqualTo(0.0);
		assertThat(actual.getValidityDate()).hasDayOfMonth(31);
		assertThat(actual.getValidityDate()).hasMonth(12);
		Mockito.verify(_subscriptionRepositoryMock, Mockito.times(1)).save(Mockito.any(Subscription.class));
	}
	
	@Test
	void recordFlight_ShouldRaiseEntityNotFoundException_WhenMemberNotExists()
	{
		//arrange
		// TODO refactor whhen User defined
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId(-1);
			
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.recordFlight(dto);
		}).withMessage("Ce membre n'existe pas");
	}
	
	@Test
	void recordSubscription_ShouldSuccess_WhenOK() throws EntityNotFoundException
	{
		//arrange
		// TODO refactor when User and Hanger endpoint defined
		Calendar today = Calendar.getInstance();
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId(9);
		dto.setAircraft("F-GCNS CESSNA C152");
		dto.setLineItem("Test");
		dto.setFlightDate(today.getTime());
		dto.setFlightHours(2.0);
		dto.setAmount(258.0);
		Flight flight = new Flight(9, "F-GCNS CESSNA C152", "Test", today.getTime(), 2.0, 258.0);
		Mockito.when(_flightDtoConverterMock.convertDtoToEntity(Mockito.any(FlightRecordDto.class))).thenReturn(flight);
		Mockito.when(_flightRepositoryMock.save(Mockito.any(Flight.class))).thenReturn(flight);
		
		// act
		Flight actual = systemUnderTest.recordFlight(dto);
		
		// assert
		assertThat(actual.getAmount()).isEqualTo(258.0);
		Mockito.verify(_flightRepositoryMock, Mockito.times(1)).save(Mockito.any(Flight.class));
	}
}
