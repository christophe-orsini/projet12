package com.ocdev.airclub.services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.dto.Aircraft;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ProxyException;

import reactor.core.publisher.Mono;

@Service
public class AircraftServiceImpl implements AircraftService
{
	@Autowired
	WebClient webclient;

	@Override
	public List<Aircraft> getAircrafts()
	{
		return (webclient
				.method(HttpMethod.GET)
				.uri("http://localhost:8080/hangar/aircrafts")				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code.is5xxServerError(), clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.bodyToFlux(Aircraft.class)
				.collectList()
				.block());
	}

	@Override
	public Optional<Aircraft> getAircraftByRegistration(String registration)
	{
		Duration timeout = Duration.ofSeconds(2);
		return webclient
				.method(HttpMethod.GET)
				.uri("http://localhost:8080/hangar/aircrafts/" + registration)				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("L'aéronef est introuvable")))
				.onStatus(code -> code.is5xxServerError(), clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.bodyToMono(Aircraft.class)
				.blockOptional(timeout);
	}
	
	@Override
	public Optional<Aircraft> getAircraft(long id)
	{
		Duration timeoutIn10Seconds = Duration.ofSeconds(10);
		return webclient
				.method(HttpMethod.GET)
				.uri("http://localhost:8080/hangar/aircrafts/id/" + id)				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("L'aéronef est introuvable")))
				.onStatus(code -> code.is5xxServerError(), clientResponse -> Mono.error(new ProxyException("Le service Hangar n'est pas accessible")))
				.bodyToMono(Aircraft.class)
				.blockOptional(timeoutIn10Seconds);
	}

	@Override
	public Aircraft selectAircraftById(long id, List<Aircraft> aircrafts)
	{
		Optional<Aircraft> aircraft = aircrafts.stream()
				.filter(a -> a.getId() == id)
				.findFirst();
		
		return aircraft.orElse(null);
	}
}
