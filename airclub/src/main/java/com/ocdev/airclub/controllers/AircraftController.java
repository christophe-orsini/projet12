package com.ocdev.airclub.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocdev.airclub.dto.Aircraft;
import com.ocdev.airclub.services.AircraftService;

@RequestMapping("/aircraft")
@Controller
public class AircraftController
{
	@Autowired
	private AircraftService _aircraftService;
	
	@GetMapping({"/list"})
	public String list(Model model)
	{	 
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
	    
		return "aircraft_list";
	}
	
	@GetMapping({"/{registration}"})
	public String getAircraft(@PathVariable @NotBlank final String registration, Model model)
	{	
		List<Aircraft> aircrafts = new ArrayList<Aircraft>();
		Optional<Aircraft> aircraft = _aircraftService.getAircraftByRegistration(registration);
		if (aircraft.isPresent())
		{
			aircrafts.add(aircraft.get());
		}
		
		model.addAttribute("aircrafts", aircrafts);
		
		return "aircraft_list";
	}
}
