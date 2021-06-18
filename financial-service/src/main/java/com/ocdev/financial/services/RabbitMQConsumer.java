package com.ocdev.financial.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ocdev.financial.dto.FlightDto;

@Component
public class RabbitMQConsumer
{
	@RabbitListener(queues = "${airclub.rabbitmq.queue}")
	public void recievedAircraft(FlightDto flight)
	{
		System.out.println("Recieved Message From RabbitMQ: " + flight);
	}
}