package com.ocdev.reservation.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dto.BookingCloseDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;
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
@RequestMapping("/reservations")
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
			@ApiParam(value = "Id de l'aéronef", required = true, example = "1") 
			@PathVariable @NotBlank final long aircraftId,
			@ApiParam(value = "Date et heure de départ", required = true, example = "2021-06-04 10:30") 
			@RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final LocalDateTime startTime,
			@ApiParam(value = "Durée du vol", example = "1.5", defaultValue = "1.0") 
			@RequestParam(name = "duration", required = false, defaultValue = "1.0") final double duration
			) throws EntityNotFoundException, ProxyException
	{
		return _reservationService.isAircaftAvailable(aircraftId, startTime, duration);
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
			@RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final LocalDateTime startTime,
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
	@PostMapping(value = "", consumes = "application/json", produces = "application/json")
	public Booking addReservation(@Valid @RequestBody final BookingCreateDto bookingCreateDto) 
			throws AlreadyExistsException, EntityNotFoundException, ProxyException
	{
		return _reservationService.createBooking(bookingCreateDto);
	}
	
	@ApiOperation(value = "Lister les réservations d'un membre", notes = "Obtenir la liste des réservations en cours d'un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "Le membre n'existe pas"),
			})
	@GetMapping(value = "/{memberId}", produces = "application/json")
	public Collection<Booking> getAllActive(@ApiParam(value = "Id du membre", required = true, example = "5ffe-d445") 
	@PathVariable final String memberId) throws EntityNotFoundException
	{
		return _reservationService.getAllBookings(memberId, false);
	}
	
	@ApiOperation(value = "Annuler une réservation", notes = "Annuler une réservation en cours")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La réservation est annulée"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "La réservation n'existe pas ou elle est clôturée"),
			})
	@DeleteMapping(value = "/{reservationId}", produces = "application/json")
	public void deleteReservation(@ApiParam(value = "Id de la réservation", required = true, example = "1") 
	@PathVariable @Positive final long reservationId) throws EntityNotFoundException
	{
		_reservationService.deleteBooking(reservationId);
	}

	@ApiOperation(value = "Clôturer une réservation", notes = "Clôturer une réservation et enregistrer le vol")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "La réservation est clôturée"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef ou le membre n'existe pas"),
			@ApiResponse(code = 502, message = "Erreur d'accés au service hangar")
			})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PutMapping(value = "/{reservationId}", produces = "application/json")
	public Booking closeReservation(
			@ApiParam(value = "Id de la réservation", required = true, example = "1") 
			@PathVariable @Positive final long reservationId,
			@Valid @RequestBody final BookingCloseDto bookingCloseDto) 
			throws EntityNotFoundException, ProxyException
	{
		return _reservationService.closeBooking(reservationId, bookingCloseDto);
	}
	
	@ApiOperation(value = "Lister les réservations d'un aéronef pour une journée", notes = "Obtenir la liste des réservations d'un membre")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "Le membre n'existe pas"),
			})
	@GetMapping(value = "/aircraft/{aircraftId}/date/{date}", produces = "application/json")
	public Collection<Booking> getAllForAircraftAndDate(
			@ApiParam(value = "Id de l'aéronef", required = true, example = "1") 
			@PathVariable @Positive final long aircraftId,
			@ApiParam(value = "Date", required = true, example = "2021-08-05") 
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date
			) throws EntityNotFoundException
	{
		return _reservationService.getAllBookings(aircraftId, date);
	}
}
