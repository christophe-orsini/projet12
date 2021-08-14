package com.ocdev.airclub.services;

import java.util.List;
import java.util.Optional;

import com.ocdev.airclub.dto.Aircraft;

public interface AircraftService
{
	public List<Aircraft> getAircrafts();
	public Optional<Aircraft> getAircraftByRegistration(String registration);
	public Aircraft selectAircraftById(long id, List<Aircraft> aircrafts);
	public Optional<Aircraft> getAircraft(long id);
}
