package com.ocdev.airclub.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String login(Model model)
	{	
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		
		model.addAttribute("aircrafts", aircrafts);
		
		return "/aircraft/list";
	}
}
