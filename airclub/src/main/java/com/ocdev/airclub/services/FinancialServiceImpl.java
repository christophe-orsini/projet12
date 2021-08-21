package com.ocdev.airclub.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.dto.Flight;
import com.ocdev.airclub.dto.InvoicePayDto;
import com.ocdev.airclub.errors.EntityNotFoundException;
import com.ocdev.airclub.errors.ProxyException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class FinancialServiceImpl implements FinancialService
{
	@Value("${app.gateway.url}")
	private String _gatewayUrl;
	
	@Autowired
	WebClient webclient;
	
	@Override
	public List<Flight> getInvoices(String memberId, boolean paid)
	{
		Duration timeout = Duration.ofSeconds(10);
		
		String endPoint = _gatewayUrl + "/financial/flights/" + memberId;
		if (paid)
		{
			endPoint += "/paid";
		}
		
		return webclient
				.get()
				.uri(endPoint)				
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service Finance n'est pas accessible")))
				.bodyToFlux(Flight.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
				.collectList()
				.block(timeout);
	}

	@Override
	public Flight getInvoice(long id)
	{
		Duration timeout = Duration.ofSeconds(10);
		
		return webclient
				.get()
				.uri(_gatewayUrl + "/financial/flights/flight/" + id)				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("Le vol n'existe pas")))
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service Finance n'est pas accessible")))
				.bodyToMono(Flight.class)
				.block(timeout);
	}

	@Override
	public double totalTime(List<Flight> flights)
	{
		double result = 0;
		
		for (Flight flight : flights)
		{
			result += flight.getFlightHours();
		}
		
		return result;
	}

	@Override
	public double totalAmount(List<Flight> flights, boolean paid)
	{
		double result = 0;
		
		for (Flight flight : flights)
		{
			if (paid) result += flight.getPayment();
			else result += flight.getAmount();
		}
		
		return result;
	}

	@Override
	public Flight payInvoice(long invoiceId, InvoicePayDto invoicePayDto)
	{
		Duration timeout = Duration.ofSeconds(10);
		
		return webclient
				.put()
				.uri(_gatewayUrl + "/financial/flights/pay/" + invoiceId)				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(invoicePayDto), InvoicePayDto.class)
				.retrieve()
				.onStatus(code -> code == HttpStatus.NOT_FOUND, clientResponse -> Mono.error(new EntityNotFoundException("La facture n'existe pas")))
				.onStatus(code -> code == HttpStatus.BAD_GATEWAY, clientResponse -> Mono.error(new ProxyException("Le service Finance n'est pas accessible")))
				.bodyToMono(Flight.class)
				.block(timeout);
		
	}
}
