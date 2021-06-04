package com.ocdev.reservation.controllers;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.proxies.HangarProxy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@RequestMapping("/reservations")
@RestController
@Validated
@Api(tags = {"API de gestion des reservations"})
public class ReservationController
{
	@Autowired
	private HangarProxy _hangarProxy;
	
	@ApiOperation(value = "Obtenir un aéronef", notes = "Obtenir un aéronef à partir de son immatriculation")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'aéronef est retourné dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@GetMapping(value = "/aircrafts/{registration}", produces = "application/json")
	public ResponseEntity<Aircraft> getAircraft(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") 
			@PathVariable @NotBlank final String registration)
			throws EntityNotFoundException
	{
		return _hangarProxy.getAircraft(registration);
	}
	
	// Obtenir les dispos d'un aéronef
	// Obtenir les aéronefs dispi d'une date
	// Reserver une aéronef
	// Lister ses réservations
	// Cloturer une réservation
	// Annuler une réservation
}
