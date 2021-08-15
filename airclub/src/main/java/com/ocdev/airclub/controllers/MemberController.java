package com.ocdev.airclub.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.ocdev.airclub.dto.Aircraft;
import com.ocdev.airclub.dto.Booking;
import com.ocdev.airclub.dto.BookingDisplayCloseDto;
import com.ocdev.airclub.dto.BookingDisplayDto;
import com.ocdev.airclub.dto.BookingNewDto;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ErrorMessage;
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
	public String planning(Model model, Principal principal, @PathVariable long aircraftId, @PathVariable String currentDate)
	{	 
		List<Aircraft> aircrafts = _aircraftService.getAircrafts();
		model.addAttribute("aircrafts", aircrafts);
		
		Aircraft aircraft = _aircraftService.selectAircraftById(aircraftId, aircrafts);
		model.addAttribute("selectedAircraft", aircraft);
	    
		LocalDate date = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		model.addAttribute("currentDate", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		List<Booking> bookings = _bookingService.getBookingForAircraftAndDay(aircraftId, date);
		model.addAttribute("bookings", bookings);
		
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String memberId = (String) oAuth2Authentication.getPrincipal().getAttribute("sub");
		
		BookingNewDto dto = new BookingNewDto();
		dto.setAircraftId(aircraftId);
		dto.setMemberId(memberId);
		dto.setDepartureDate(date);
		dto.setArrivalDate(date);
		model.addAttribute("newBooking", dto);
		
		return "/member/planning";
	}
	
	@PostMapping(value = "/book", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String bookAircraft(Model model, @Valid final BookingNewDto bookingNewDto)
	{	
		Booking response = _bookingService.createBooking(bookingNewDto);
		
		if (response != null)
		{
			model.addAttribute("message", "La réservation n° " + response.getId() + " est enregistrée");
			return "/theme/validation";
		}
			
		model.addAttribute("exceptionMessage", new ErrorMessage(0, null, "La réservation n'a pas été crée"));
		return "/theme/error";
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
	
	@GetMapping("/bookings")
	public String activeBookings(Model model, Principal principal)
	{
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String memberId = (String) oAuth2Authentication.getPrincipal().getAttribute("sub");
		List<BookingDisplayDto> bookings = _bookingService.getBookings(memberId, false);
		model.addAttribute("bookings", bookings);
	    
		return "/member/bookings";
	}
	
	@GetMapping("/bookings/delete/{id}")
	public String deleteBooking(@PathVariable long id)
	{
		_bookingService.cancelBooking(id);
	    
		return "redirect:/member/bookings";
	}
	
	@GetMapping("/bookings/close/{id}")
	public String closeBooking(Model model, @PathVariable long id) throws EntityNotFoundException
	{
		BookingDisplayCloseDto closedBooking = _bookingService.initBookingCloseDto(id);
		model.addAttribute("closedBooking", closedBooking);
	    
		return "/member/close_booking";
	}
	
	@PostMapping("/close")
	public String formCloseAction(@ModelAttribute("closedBooking") BookingDisplayCloseDto closedBooking, Model model)
	{
		_bookingService.closeBooking(closedBooking);
		   
		return "redirect:/member/bookings";
	}
}
