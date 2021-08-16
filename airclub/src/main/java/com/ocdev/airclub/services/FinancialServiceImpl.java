package com.ocdev.airclub.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ocdev.airclub.dto.Flight;
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
				.bodyToFlux(Flight.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
				.collectList()
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
	
}
