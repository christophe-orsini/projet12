package com.ocdev.financial.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ocdev.financial.converters.IDtoConverter;
import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;
import com.ocdev.financial.messages.RegisterFlightMessage;

@Service
public class FinancialServiceImpl implements FinancialService
{
	@Value("${financial.subscription.amount}")
	private double _charge;
	
	private FlightRepository _flightRepository;
	private SubscriptionRepository _subscriptionRepository;
	private IDtoConverter<Flight, FlightRecordDto> _flightRecordDtoConverter;
	
	public FinancialServiceImpl(
			@Autowired FlightRepository flightRepository, 
			@Autowired SubscriptionRepository subscriptionRepository,
			@Autowired IDtoConverter<Flight, FlightRecordDto> flightRecordDtoConverter)
	{
		_flightRepository = flightRepository;
		_subscriptionRepository = subscriptionRepository;
		_flightRecordDtoConverter = flightRecordDtoConverter;
	}

	@Override
	public Collection<Flight> getAllFlights(long memberId)
	{
		// TODO check if memberId exists
		
		return _flightRepository.findByMemberIdAndClosed(memberId, false);
	}

	@Override
	public Subscription getLastSubscription(long memberId) throws EntityNotFoundException
	{
		// TODO check if memberId exists
		
		Optional<Subscription> subscription = _subscriptionRepository.findLastSubscriptionByMemberId(memberId);
		if (!subscription.isPresent()) throw new EntityNotFoundException("Ce membre n'a pas de cotisation en cours");
		
		return subscription.get();
	}

	@Override
	public Subscription recordSubscription(SubscriptionDto subscriptionDto) throws EntityNotFoundException, AlreadyExistsException
	{
		// TODO check if memberId exists
		if (subscriptionDto.getMemberId() < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		Optional<Subscription> activeSubscription = _subscriptionRepository.findLastSubscriptionByMemberId(subscriptionDto.getMemberId());
		if (activeSubscription.isPresent() && activeSubscription.get().getValidityDate() != null && activeSubscription.get().getValidityDate().after(new Date()))
		{
			throw new AlreadyExistsException("Ce membre a déjà une cotisation valide");
		}
		
		double value = subscriptionDto.getAmount();
		if (value < 0) value = _charge;
		
		Calendar today = Calendar.getInstance();
		Calendar validity = new GregorianCalendar(today.get(Calendar.YEAR), 11, 31);
		
		Subscription subscription = new Subscription(subscriptionDto.getMemberId(), today.getTime(), value);
		subscription.setValidityDate(validity.getTime());
		
		_subscriptionRepository.save(subscription);
		return subscription;
	}

	@Override
	public Flight recordFlight(FlightRecordDto flightDto) throws EntityNotFoundException
	{
		// TODO check if memberId exists
		if (flightDto.getMemberId() < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		Flight flight = _flightRecordDtoConverter.convertDtoToEntity(flightDto);
		
		_flightRepository.save(flight);
		return flight;
	}
	
	@RabbitListener(queues = "${finance.rabbitmq.queue}")
	public void recievedAircraft(RegisterFlightMessage message)
	{
		// TODO
		System.out.println("Recieved Message From RabbitMQ: " + message);
	}
}
