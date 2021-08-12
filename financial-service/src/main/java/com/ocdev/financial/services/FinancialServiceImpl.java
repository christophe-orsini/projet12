package com.ocdev.financial.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

@Service
public class FinancialServiceImpl implements FinancialService
{
	@Value("${financial.subscription.amount}")
	private double _charge;
	
	private FlightRepository _flightRepository;
	private SubscriptionRepository _subscriptionRepository;
	private IDtoConverter<Flight, FlightRecordDto> _flightRecordDtoConverter;
	private HangarProxy _hangarProxy;
	
	public FinancialServiceImpl(
			@Autowired FlightRepository flightRepository, 
			@Autowired SubscriptionRepository subscriptionRepository,
			@Autowired IDtoConverter<Flight, FlightRecordDto> flightRecordDtoConverter,
			@Autowired HangarProxy hangarProxy)
	{
		_flightRepository = flightRepository;
		_subscriptionRepository = subscriptionRepository;
		_flightRecordDtoConverter = flightRecordDtoConverter;
		_hangarProxy = hangarProxy;
	}

	@Override
	public Collection<Flight> getAllFlights(long memberId)
	{
		return _flightRepository.findByMemberIdAndClosed(memberId, false);
	}

	@Override
	public Subscription getLastSubscription(long memberId) throws EntityNotFoundException
	{
		Optional<Subscription> subscription = _subscriptionRepository.findLastSubscriptionByMemberId(memberId);
		if (!subscription.isPresent()) throw new EntityNotFoundException("Ce membre n'a pas de cotisation en cours");
		
		return subscription.get();
	}

	@Override
	public Subscription recordSubscription(SubscriptionDto subscriptionDto) throws EntityNotFoundException, AlreadyExistsException
	{
		// TODO check if memberId exists
		if (subscriptionDto.getMemberId() < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		LocalDate today = LocalDate.now();
		
		Optional<Subscription> activeSubscription = _subscriptionRepository.findLastSubscriptionByMemberId(subscriptionDto.getMemberId());
		if (activeSubscription.isPresent() 
				&& activeSubscription.get().getValidityDate() != null 
				&& activeSubscription.get().getValidityDate().isAfter(today))
		{
			throw new AlreadyExistsException("Ce membre a déjà une cotisation valide");
		}
		
		double value = subscriptionDto.getAmount();
		if (value < 0) value = _charge;
		
		LocalDate validity = LocalDate.of(today.getYear(), 12, 31);
		
		Subscription subscription = new Subscription(subscriptionDto.getMemberId(), today, value);
		subscription.setValidityDate(validity);
		
		return _subscriptionRepository.save(subscription);
	}

	@Override
	public Flight recordFlight(FlightRecordDto flightDto) throws EntityNotFoundException
	{
		// TODO check if memberId exists
		if (flightDto.getMemberId() < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		Flight flight = _flightRecordDtoConverter.convertDtoToEntity(flightDto);
		
		return _flightRepository.save(flight);
	}
	
	@RabbitListener(queues = "${finance.rabbitmq.queue}")
	public void registerEndedFlight(RegisterFlightMessage message) throws EntityNotFoundException, ProxyException
	{
		// TODO check if memberId exists
		if (message.getMemberId() < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		//Get aircraft
		Aircraft aircraft = _hangarProxy.getAircraftById(message.getAircraftId());
				
		Flight flight = new Flight(message.getMemberId(), message.getDescription(), message.getFlightDate(), message.getDuration());
		flight.setAircraft(aircraft.getRegistration() + " " + aircraft.getMake() + " " + aircraft.getModel());
		flight.setAmount(message.getDuration() * message.getHourlyRate());
		
		_flightRepository.save(flight);
	}
}
