package com.ocdev.financial.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.EntityNotFoundException;

@Service
public class FinancialServiceImpl implements FinancialService
{
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
		if (!subscription.isPresent()) throw new EntityNotFoundException("Ce membre n'a pas d'adhésion en cours");
		
		return subscription.get();
	}
}
