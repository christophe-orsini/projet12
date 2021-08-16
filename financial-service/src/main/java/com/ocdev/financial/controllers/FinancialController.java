package com.ocdev.financial.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;
import com.ocdev.financial.services.FinancialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@RestController
@Validated
@Api(tags = {"API de gestion des finances"})
public class FinancialController
{
	@Autowired 
	private FinancialService _financialService;
	
	@ApiOperation(value = "Enregistrement d'un vol", notes = "Enregsitrement d'un nouveau vol")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Le vol est enregistré"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "Le membre n'existe pas")
			})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/flights", produces = "application/json")
	public  ResponseEntity<Flight> recordFlight(@Valid @RequestBody final FlightRecordDto flightRecordDto) throws EntityNotFoundException
	{
		Flight flight = _financialService.recordFlight(flightRecordDto);
		return new ResponseEntity<Flight>(flight, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Obtenir l'encours d'un membre", notes = "Obtenir la liste des vols dûs par un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise")
			})
	@GetMapping(value = "/flights/{memberId}", produces = "application/json")
	public ResponseEntity<Collection<Flight>> getFlights(
			@ApiParam(value = "Id du membre", required = true, example = "2ffe-5a55") @PathVariable final String memberId)
			throws EntityNotFoundException
	{
		Collection<Flight> flights = _financialService.getAllFlights(memberId, false);
		return new ResponseEntity<Collection<Flight>>(flights, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtenir les vols d'un membre", notes = "Obtenir la liste des vols payés par un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise")
			})
	@GetMapping(value = "/flights/{memberId}/paid", produces = "application/json")
	public ResponseEntity<Collection<Flight>> getPaidFlights(
			@ApiParam(value = "Id du membre", required = true, example = "2ffe-5a55") @PathVariable final String memberId)
			throws EntityNotFoundException
	{
		Collection<Flight> flights = _financialService.getAllFlights(memberId, true);
		return new ResponseEntity<Collection<Flight>>(flights, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtenir les informations d'une cotisation", notes = "Obtenir les information concernant la dernière cotisation d'un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La cotisation est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "Pas de cotisation pour ce membre")
			})
	@GetMapping(value = "/subscriptions/{memberId}", produces = "application/json")
	public ResponseEntity<Subscription> getSubscription(
			@ApiParam(value = "Id du membre", required = true, example = "2ffe-5a55") @PathVariable final String memberId)
			throws EntityNotFoundException
	{
		Subscription subscription = _financialService.getLastSubscription(memberId);
		return new ResponseEntity<Subscription>(subscription, HttpStatus.OK);
	}

	@ApiOperation(value = "Enregistrer une cotisation", notes = "Enregistrer la cotisation d'un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "La cotisation est enregistrée"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "Le membre n'existe pas"),
			@ApiResponse(code = 460, message = "Le membre a déjà une cotisation valide")
			})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/subscriptions", produces = "application/json")
	public ResponseEntity<Subscription> recordSubscription(@Valid @RequestBody final SubscriptionDto subscriptionDto)
			throws EntityNotFoundException, AlreadyExistsException
	{
		Subscription subscription = _financialService.recordSubscription(subscriptionDto);
		return new ResponseEntity<Subscription>(subscription, HttpStatus.CREATED);
	}
}
