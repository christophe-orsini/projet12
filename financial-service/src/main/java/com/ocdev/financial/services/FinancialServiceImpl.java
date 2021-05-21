package com.ocdev.financial.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;

@Service
public class FinancialServiceImpl implements FinancialService
{
	@Value("${financial.subscription.amount}")
	private double charge;
	
	private FlightRepository _flightRepository;
	private SubscriptionRepository _subscriptionRepository;
	
	public FinancialServiceImpl(@Autowired FlightRepository flightRepository, @Autowired SubscriptionRepository subscriptionRepository)
	{
		_flightRepository = flightRepository;
		_subscriptionRepository = subscriptionRepository;
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
	public Subscription recordSubscription(long memberId, double amount) throws EntityNotFoundException, AlreadyExistsException
	{
		// TODO check if memberId exists
		if (memberId < 0) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		Optional<Subscription> activeSubscription = _subscriptionRepository.findLastSubscriptionByMemberId(memberId);
		if (activeSubscription.isPresent() && activeSubscription.get().getValidityDate() != null && activeSubscription.get().getValidityDate().after(new Date()))
		{
			throw new AlreadyExistsException("Ce membre a déjà une cotisation valide");
		}
		
		double value = amount;
		if (value < 0)
		{
			value = charge;
		}
		Calendar today = Calendar.getInstance();
		Calendar validity = new GregorianCalendar(today.get(Calendar.YEAR), 11, 31);
		
		Subscription subscription = new Subscription(memberId, today.getTime(), value);
		subscription.setValidityDate(validity.getTime());
		
		return _subscriptionRepository.save(subscription);
	}
	
	
}
