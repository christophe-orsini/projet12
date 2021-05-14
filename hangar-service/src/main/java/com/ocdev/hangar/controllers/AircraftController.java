package com.ocdev.hangar.controllers;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;
import com.ocdev.hangar.errors.AlreadyExistsException;
import com.ocdev.hangar.errors.EntityNotFoundException;
import com.ocdev.hangar.services.AircraftService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@FeignClient(contextId="dummies", name="proxy-service")
@RefreshScope
@RequestMapping("/aircrafts")
@RestController
@Validated
@Api(tags = {"API d'accès au hangar aéronefs"})
public class AircraftController
{
	@Autowired 
	private AircraftService _aircraftService;
	
	@ApiOperation(value = "Obtenir un aéronef", notes = "Obtenir un aéronef à partir de son immatriculation")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'aéronef est retourné dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@GetMapping(value = "/{registration}", produces = "application/json")
	public ResponseEntity<Aircraft> getAircraft(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration)
			throws EntityNotFoundException
	{
		Aircraft aircraft = _aircraftService.getByRegistration(registration);
		return new ResponseEntity<Aircraft>(aircraft, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lister les aéronefs", notes = "Obtenir la liste des aéronefs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La liste est retournée dans le corps de la réponse"),
			@ApiResponse(code = 401, message = "Authentification requise")
			})
	@GetMapping(produces = "application/json")
	public ResponseEntity<Collection<Aircraft>> getAll()
	{
		Collection<Aircraft> aircrafts = _aircraftService.getAll();
		return new ResponseEntity<Collection<Aircraft>>(aircrafts, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Ajout d'un aéronef", notes = "Ajout d'un nouvel aéronef")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "L'aéronef est correctement créé"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 460, message = "Un aéronef avec la même immatriculation existe déjà")
			})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(produces = "application/json")
	public Aircraft addAircraft(@Valid @RequestBody final AircraftDto aircraftCreateDto) throws AlreadyExistsException
	{
		return _aircraftService.create(aircraftCreateDto);
	}
	
	@ApiOperation(value = "Changer le tarif d'un aéronef", notes = "Changer le tarif d'un aéronef existant")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Le tarif est changé"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@PatchMapping(value = "/{registration}/price/{price}", produces = "application/json")
	public Aircraft changePrice(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration, 
			@ApiParam(value = "Nouveau tarif", required = true, example = "129") @PathVariable @Min(0) final int price)
			throws EntityNotFoundException
	{
		return _aircraftService.updatePrice(registration, price);
	}
	
	@ApiOperation(value = "Modifier un aéronef", notes = "Modification d'un aéronef existant")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'aéronef est correctement modifié"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@PutMapping(value = "/{registration}", produces = "application/json")
	public Aircraft updateAircraft(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration, 
			@Valid @RequestBody final AircraftDto aircraftCreateDto)
			throws EntityNotFoundException
	{
		return _aircraftService.updateDatas(aircraftCreateDto);
	}
	
	@ApiOperation(value = "Changer la disponibilité d'un aéronef", notes = "Changer la disponibilité d'un aéronef existant")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La disponibilité est changée"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@PatchMapping(value = "/{registration}/available/{availability}", produces = "application/json")
	public Aircraft changeAvailability(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration, 
			@ApiParam(value = "Disponibilité", required = true, example = "true") @PathVariable final boolean availability)
			throws EntityNotFoundException
	{
		return _aircraftService.changeAvailability(registration, availability);
	}
	
	@ApiOperation(value = "Changer le moment de la prochaine maintenance", notes = "Changer l'horamètre de la prochaine maintenance")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'horamètre de la prochaine maintenance est changé"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@PatchMapping(value = "/{registration}/maintenance/{duration}", produces = "application/json")
	public Aircraft nextMaintenance(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration, 
			@ApiParam(value = "Durée à ajouter", required = true, example = "1234.5") @PathVariable final float duration)
			throws EntityNotFoundException
	{
		return _aircraftService.registerNextMaintenanceSchedule(registration, duration);
	}
	
	@ApiOperation(value = "Obtenir le potentiel d'un aéronef", notes = "Obtenir le potentiel restant avant maintenance d'un aéronef")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Le potentiel est retourné"),
			@ApiResponse(code = 401, message = "Authentification requise"),
			@ApiResponse(code = 404, message = "L'aéronef n'existe pas")
			})
	@GetMapping(value = "/left/{registration}", produces = "application/json")
	public double getLeftTime(
			@ApiParam(value = "Immatriculation de l'aéronef", required = true, example = "F-HAAA") @PathVariable @NotBlank final String registration)
			throws EntityNotFoundException
	{
		return _aircraftService.getLeftTime(registration);
	}
}
