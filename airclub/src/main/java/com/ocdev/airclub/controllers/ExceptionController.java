package com.ocdev.airclub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ocdev.airclub.errors.AlreadyExistsException;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ErrorMessage;
import com.ocdev.airclub.errors.ProxyException;
import com.ocdev.airclub.errors.ServicesHttpStatus;

@ControllerAdvice
@Controller
class ExceptionController
{
	@ExceptionHandler(AlreadyExistsException.class)
	public String onAlreadyExistsException(AlreadyExistsException e, Model model)
	{
		ServicesHttpStatus status = ServicesHttpStatus.SERVICES_DUPLICATE;
		ErrorMessage error = new ErrorMessage(status.getCode(), status.getName(), e.getMessage());
		
		model.addAttribute("exceptionMessage", error);
		return "/utils/error";
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	String onEntityNotFoundException(EntityNotFoundException e, Model model)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorMessage error = new ErrorMessage(status.value(), status.getReasonPhrase(), e.getMessage());
		
		model.addAttribute("exceptionMessage", error);
		return "/utils/error";
	}
	
	@ExceptionHandler(ProxyException.class)
	String onProxyException(ProxyException e, Model model)
	{
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		ErrorMessage error = new ErrorMessage(status.value(), status.getReasonPhrase(), e.getMessage());
		
		model.addAttribute("exceptionMessage", error);
		return "/utils/error";
	}
	
	@ExceptionHandler(WebClientResponseException.class)
	public String onWebClientResponseException(WebClientResponseException e, Model model)
	{
		ErrorMessage error = new ErrorMessage(e.getRawStatusCode(),  e.getStatusText(), e.getMessage());
		
		model.addAttribute("exceptionMessage", error);
		return "/utils/error";
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public String onIllegalStateException(IllegalStateException e, Model model) 
	{
		HttpStatus status = HttpStatus.GATEWAY_TIMEOUT;
		ErrorMessage error = new ErrorMessage(status.value(),  status.getReasonPhrase(), "Le serveur est trop long pour répondre");
		
		model.addAttribute("exceptionMessage", error);
		return "/utils/error";
    }
}