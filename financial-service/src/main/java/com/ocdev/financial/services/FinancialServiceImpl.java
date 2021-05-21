package com.ocdev.financial.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.MembershipRepository;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Membership;
import com.ocdev.financial.errors.EntityNotFoundException;

@Service
public class FinancialServiceImpl implements FinancialService
{
	private FlightRepository _flightRepository;
	private MembershipRepository _membershipRepository;
	
	public FinancialServiceImpl(@Autowired FlightRepository flightRepository, @Autowired MembershipRepository membershipRepository)
	{
		_flightRepository = flightRepository;
		_membershipRepository = membershipRepository;
	}

	@Override
	public Collection<Flight> getAllFlights(long memberId)
	{
		// TODO check if memberId exists
		
		return _flightRepository.findByMemberIdAndClosed(memberId, false);
	}

	@Override
	public Membership getMembership(long memberId) throws EntityNotFoundException
	{
		// TODO check if memberId exists
		
		Optional<Membership> membership = _membershipRepository.findLastMembershipByMemberId(memberId);
		if (!membership.isPresent()) throw new EntityNotFoundException("Ce membre n'a pas d'adhésion en cours");
		
		return membership.get();
	}
}
