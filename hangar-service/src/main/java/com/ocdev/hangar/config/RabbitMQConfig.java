package com.ocdev.hangar.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig
{
	@Value("${airclub.rabbitmq.queue}")
	String airclubQueueName;
	
	@Value("${hangar.rabbitmq.queue}")
	String hangarQueueName;

	@Value("${spring.rabbitmq.username}")
	String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Bean
	Queue airclubQueue()
	{
		return new Queue(airclubQueueName, false);
	}
	
	@Bean
	Queue hangarQueue()
	{
		return new Queue(hangarQueueName, false);
	}
	
	@Bean
    public Jackson2JsonMessageConverter converter()
	{
        return new Jackson2JsonMessageConverter();
    }
}