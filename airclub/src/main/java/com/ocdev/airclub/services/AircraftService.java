package com.ocdev.airclub.services;

import java.util.List;
import java.util.Optional;

import com.ocdev.airclub.dto.Aircraft;

public interface AircraftService
{
	public List<Aircraft> getAircrafts();
	public List<Aircraft> getAircrafts2();
	public Optional<Aircraft> getAircraftByRegistration(String registration);
}
