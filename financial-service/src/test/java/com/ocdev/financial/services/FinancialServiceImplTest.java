package com.ocdev.financial.services;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;

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
	@Mock private EmailService _emailServiceMock;
	@Mock private EmailContentBuilder _emailContentBuilderMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 systemUnderTest = new FinancialServiceImpl(_flightRepositoryMock, _subscriptionRepositoryMock, _flightDtoConverterMock,
				 _hangarProxyMock, _emailServiceMock, _emailContentBuilderMock);
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
		dto.setMemberId(null);
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
		LocalDate date = LocalDate.of(2021,12,31);
		Subscription subscription = new Subscription("9", date, 123.45);
		subscription.setValidityDate(date);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyString())).thenReturn(Optional.of(subscription));
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId("9");
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
		LocalDate today = LocalDate.now();
		Subscription subscription = new Subscription("9", today, 123.45);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyString())).thenReturn(Optional.of(subscription));
		Mockito.when(_subscriptionRepositoryMock.save(Mockito.any(Subscription.class))).thenReturn(subscription);
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId("9");
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
		LocalDate today = LocalDate.now();
		Subscription subscription = new Subscription("9", today, 123.45);
		subscription.setId(99);
		Mockito.when(_subscriptionRepositoryMock.findLastSubscriptionByMemberId(Mockito.anyString())).thenReturn(Optional.of(subscription));
		
		SubscriptionDto dto = new SubscriptionDto();
		dto.setMemberId("9");
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
		dto.setMemberId(null);
			
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
		// TODO refactor when User defined
		LocalDate today = LocalDate.now();
		FlightRecordDto dto = new FlightRecordDto();
		dto.setMemberId("9");
		dto.setAircraft("F-GCNS CESSNA C152");
		dto.setLineItem("Test");
		dto.setFlightDate(today);
		dto.setFlightHours(2.0);
		dto.setAmount(258.0);
		Flight flight = new Flight("9", "Test", today, 2.0);
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
		message.setMemberId(null);
			
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
		message.setMemberId("9");
		message.setAircraftId(8);
		Mockito.when(_hangarProxyMock.getAircraftById(Mockito.anyLong())).thenThrow(ProxyException.class);
		
		// act & assert
		assertThatExceptionOfType(ProxyException.class).isThrownBy(() ->
		{
			systemUnderTest.registerEndedFlight(message);
		});
	}
	
	@Test
	void registerEndedFlight_ShouldSuccess_WhenOK() throws EntityNotFoundException, ProxyException, MessagingException
	{
		//arrange
		// TODO refactor when User defined
		LocalDate now = LocalDate.now();
		RegisterFlightMessage message = new RegisterFlightMessage("Johne", "Doe", "dummy@domain.tld", "9", 8, "Dummy", new Date(), 1.5, 129);
				
		Aircraft aircraft = new Aircraft();
		Mockito.when(_hangarProxyMock.getAircraftById(Mockito.anyLong())).thenReturn(aircraft);
		
		Flight flight = new Flight("9", "", now, 1.5);
		Mockito.when(_flightRepositoryMock.save(Mockito.any(Flight.class))).thenReturn(flight);
		
		Mockito.when(_emailContentBuilderMock.buildInvoiceEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(Flight.class)))
			.thenReturn("");
		
		Mockito.doNothing().when(_emailServiceMock).sendEmailHtml(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());		
		
		// act
		systemUnderTest.registerEndedFlight(message);
		
		// assert
		Mockito.verify(_flightRepositoryMock, Mockito.times(1)).save(Mockito.any(Flight.class));
		Mockito.verify(_emailServiceMock, Mockito.times(1)).sendEmailHtml("dummy@domain.tld", "", "", "");	
	}
}
