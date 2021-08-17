package com.ocdev.airclub.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.ocdev.airclub.dto.Flight;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.services.FinancialService;

@RequestMapping("/financial")
@Controller
public class FinancialController
{
	private final TemplateEngine _templateEngine;
	
	@Autowired
    private ServletContext _servletContext;

	@Autowired
	private FinancialService _financialService;
	
	public FinancialController(TemplateEngine templateEngine)
	{
        _templateEngine = templateEngine;
    }
	
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
	
	@RequestMapping(path = "/invoice/pdf/{id}")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable long id)
    		throws IOException
	{
		OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
		String givenName = (String) oAuth2Authentication.getPrincipal().getAttribute("given_name");
				
		String familyName = (String) oAuth2Authentication.getPrincipal().getAttribute("family_name");
				
		String email = (String) oAuth2Authentication.getPrincipal().getAttribute("email");
				
		Flight invoice = _financialService.getInvoice(id);
		
        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, _servletContext);
        context.setVariable("givenName", givenName);
        context.setVariable("familyName", familyName);
        context.setVariable("email", email);
        context.setVariable("invoice", invoice);
        
        String invoiceHtml = _templateEngine.process("financial_invoice_pdf", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8089");

        /* Call convert method */
        HtmlConverter.convertToPdf(invoiceHtml, target, converterProperties);  

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf") 
//                .contentType(MediaType.APPLICATION_PDF) 
//                .body(bytes);     
    }
}
