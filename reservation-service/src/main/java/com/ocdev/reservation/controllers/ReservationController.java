package com.ocdev.reservation.controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.AlreadyExistsException;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.errors.ProxyException;
import com.ocdev.reservation.services.ReservationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
 
@RefreshScope
@RestController
@Validated
@Api(tags = {"API de gestion des reservations"})
public class ReservationController
{
	@Autowired 
	private ReservationService _reservationService;
	
	@ApiOperation(value = "Obtenir la disponibilité d'un aéronef", notes = "Obtenir la disponibilité d'un aéronef pour une heure et une durée")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'aéronef est disponible"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas"),
			@ApiResponse(code = 502, message = "Erreur d'accés au service hangar")
			})
	@GetMapping(value = "/aircrafts/available/{registration}", produces = "application/json")
	public boolean isAircraftAvailable(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") 
			@PathVariable @NotBlank final String registration,
			@ApiParam(value = "Date et heure de départ", required = true, example = "2021-06-04 10:30") 
			@RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final Date startTime,
			@ApiParam(value = "Durée du vol", example = "1.5", defaultValue = "1.0") 
			@RequestParam(name = "duration", required = false, defaultValue = "1.0") final double duration
			) throws EntityNotFoundException, ProxyException
	{
		return _reservationService.isAircaftAvailable(registration, startTime, duration);
	}
	
	@ApiOperation(value = "Obtenir la liste des aéronefs disponibles", notes = "Obtenir la liste des aéronefs disponibles pour une dheure et une durée")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 502, message = "Erreur d'accés au service hangar")
			})
	@GetMapping(value = "/aircrafts/available", produces = "application/json")
	public Collection<Aircraft> availableAircrafts(
			@ApiParam(value = "Date et heure de départ", required = true, example = "2021-06-04 10:30") 
			@RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final Date startTime,
			@ApiParam(value = "Durée du vol", example = "1.5", defaultValue = "1.0") 
			@RequestParam(name = "duration", required = false, defaultValue = "1.0") final double duration
			) throws ProxyException
	{
		return _reservationService.availableAircrafts(startTime, duration);
	}
	
	@ApiOperation(value = "Réserver un aéronef", notes = "Réserver une aéronef pour une heure et une durée")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "L'aéronef est réservé"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef ou le membre n'existe pas"),
			@ApiResponse(code = 460, message = "Cet aéronef est déjà réservé pour la même période"),
			@ApiResponse(code = 502, message = "Erreur d'accés au service hangar")
			})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/reservations", produces = "application/json")
	public Booking addBooking(@Valid @RequestBody final BookingCreateDto bookingCreateDto) 
			throws AlreadyExistsException, EntityNotFoundException, ProxyException
	{
		return _reservationService.createBooking(bookingCreateDto);
	}
	
	// Lister ses réservations
	// Cloturer une réservation
	// Annuler une réservation
}
