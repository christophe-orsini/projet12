package com.ocdev.airclub.controllers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ocdev.airclub.dto.Aircraft;
import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingNewDto;
import com.ocdev.airclub.services.AircraftService;
import com.ocdev.airclub.services.BookingService;

@RequestMapping("/member")
@Controller
public class MemberController
{
	@Autowired
	private AircraftService _aircraftService;
	
	@Autowired
	private BookingService _bookingService;
	
	@GetMapping("/planning")
	public String planning(Model model)
	{	 
		LocalDate date = LocalDate.now();
		model.addAttribute("currentDate", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
	    
		return "/member/planning";
	}
	
	@GetMapping("/planning/{aircraftId}/date/{currentDate}")
	public String planning(Model model, @PathVariable long aircraftId, @PathVariable String currentDate)
	{	 
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
		
		Aircraft aircraft = _aircraftService.selectAircraftById(aircraftId, aircrafts);
		model.addAttribute("selectedAircraft", aircraft);
	    
		LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		model.addAttribute("currentDate", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		List<Booking> bookings = _bookingService.getBookingForAircraftAndDay(aircraftId, date);
		model.addAttribute("bookings", bookings);
		
		BookingNewDto dto = new BookingNewDto();
		dto.setAircraftId(aircraftId);
		dto.setMemberId(1L);
		dto.setDepartureDate(date);
		dto.setArrivalDate(date);
		model.addAttribute("newBooking", dto);
		
		return "/member/planning";
	}
	
	@PostMapping(value = "/book", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String bookAircraft(Model model, @Valid final BookingNewDto bookingNewDto)
	{	 
		_bookingService.createBooking(bookingNewDto);
		
		return "redirect:/member/planning";
	}
	
	@GetMapping("/before/{currentDate}")
	public String before(Model model, @PathVariable String currentDate)
	{	 
		LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		date = date.minusDays(1);
		model.addAttribute("currentDate", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
	    
		return "/member/planning";
	}
	
	@GetMapping("/after/{currentDate}")
	public String after(Model model, @PathVariable String currentDate)
	{	
		LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		date = date.plusDays(1);
		model.addAttribute("currentDate", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
	    
		return "/member/planning";
	}
}
