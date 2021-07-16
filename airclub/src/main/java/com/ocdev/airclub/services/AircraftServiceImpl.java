package com.ocdev.airclub.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.dto.Aircraft;

@Service
public class AircraftServiceImpl implements AircraftService
{
	@Autowired
	WebClient webclient;

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

	@Override
	public Optional<Aircraft> getAircraftByRegistration(String registration)
	{
		Duration timeoutIn10Seconds = Duration.ofSeconds(10);
		return webclient
				.method(HttpMethod.GET)
				.uri("http://localhost:8080/hangar/aircrafts/" + registration)				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(Aircraft.class)
				.blockOptional(timeoutIn10Seconds);
	}
}
