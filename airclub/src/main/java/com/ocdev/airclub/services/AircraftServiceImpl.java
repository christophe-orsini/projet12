package com.ocdev.airclub.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ocdev.airclub.dto.Aircraft;

@Service
public class AircraftServiceImpl implements AircraftService
{

	@Override
	public List<Aircraft> getAircrafts()
	{
		List<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1L);
		aircraft.setRegistration("F-GCNS");
		aircraft.setMake("CESSNA");
		aircraft.setModel("C152");
		aircraft.setHourlyRate(119);
		
		aircrafts.add(aircraft);
		
		return aircrafts;
	}
	
	@Override
	public List<Aircraft> getAircrafts2()
	{
		List<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Aircraft aircraft = new Aircraft();
		aircraft.setId(1L);
		aircraft.setRegistration("F-GHNY");
		aircraft.setMake("CESSNA");
		aircraft.setModel("C152");
		aircraft.setHourlyRate(119);
		
		aircrafts.add(aircraft);
		
		return aircrafts;
	}
}
