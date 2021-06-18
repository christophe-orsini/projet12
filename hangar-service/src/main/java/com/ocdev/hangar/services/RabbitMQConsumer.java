package com.ocdev.hangar.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ocdev.hangar.dto.FlightDto;

@Component
public class RabbitMQConsumer
{
	@RabbitListener(queues = "${airclub.rabbitmq.queue}")
	public void recievedAircraft(FlightDto flight)
	{
		System.out.println("Recieved Message From RabbitMQ: " + flight);
	}
}