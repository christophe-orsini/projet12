package com.ocdev.financial.config;

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
	
	@Value("${finance.rabbitmq.queue}")
	String financeQueueName;

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
		return new Queue(financeQueueName, false);
	}
	
	@Bean
    public Jackson2JsonMessageConverter converter()
	{
        return new Jackson2JsonMessageConverter();
    }
}