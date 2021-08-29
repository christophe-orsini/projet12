package com.ocdev.financial.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocdev.financial.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>
{
	public Collection<Flight> findByMemberIdAndClosed(String memberId, boolean closed);
}
