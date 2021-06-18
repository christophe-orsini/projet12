package com.ocdev.reservation.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ocdev.reservation.dto.FlightDto;


@Service
public class RabbitMQSender
{	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${reservation.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${airclub.rabbitmq.routingkey}")
	private String airclubRoutingkey;	
	
	@Value("${hangar.rabbitmq.routingkey}")
	private String hangarRoutingkey;	
	
	@Value("${finance.rabbitmq.routingkey}")
	private String financeRoutingkey;	
	
	public void registerFlight(FlightDto flight)
	{
		rabbitTemplate.convertAndSend(exchange, airclubRoutingkey, flight);
	}
}
