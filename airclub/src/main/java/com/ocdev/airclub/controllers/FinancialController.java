package com.ocdev.airclub.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocdev.airclub.dto.Flight;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.services.FinancialService;

@RequestMapping("/financial")
@Controller
public class FinancialController
{
	@Autowired
	private FinancialService _financialService;
	
	@GetMapping("/invoices")
	public String activeInvoices(Model model, Principal principal)
	{
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String memberId = (String) oAuth2Authentication.getPrincipal().getAttribute("sub");
		List<Flight> flights = _financialService.getInvoices(memberId, false);
		model.addAttribute("flights", flights);
		
		model.addAttribute("totalTime", _financialService.totalTime(flights));
		model.addAttribute("totalAmount", _financialService.totalAmount(flights, false));
		
		model.addAttribute("btn_1", "active");
		model.addAttribute("btn_2", "");
	    
		return "financial_invoices";
	}
	
	@GetMapping("/invoices/paid")
	public String paidInvoices(Model model, Principal principal)
	{
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String memberId = (String) oAuth2Authentication.getPrincipal().getAttribute("sub");
		List<Flight> flights = _financialService.getInvoices(memberId, true);
		model.addAttribute("flights", flights);
	    
		model.addAttribute("totalTime", _financialService.totalTime(flights));
		model.addAttribute("totalAmount", _financialService.totalAmount(flights, true));
		
		model.addAttribute("btn_1", "");
		model.addAttribute("btn_2", "active");
		
		return "financial_invoices";
	}
	
	@GetMapping("/invoice/{id}")
	public String getInvoice(Model model, Principal principal, @PathVariable long id) throws EntityNotFoundException
	{
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String givenName = (String) oAuth2Authentication.getPrincipal().getAttribute("given_name");
		model.addAttribute("givenName", givenName);
		
		String familyName = (String) oAuth2Authentication.getPrincipal().getAttribute("family_name");
		model.addAttribute("familyName", familyName); 
		
		String email = (String) oAuth2Authentication.getPrincipal().getAttribute("email");
		model.addAttribute("email", email); 
		
		Flight invoice = _financialService.getInvoice(id);
		model.addAttribute("invoice", invoice);
	    
		return "financial_invoice";
	}
}
