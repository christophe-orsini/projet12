package com.ocdev.reservation.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig
{
	@Value("${reservation.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${hangar.rabbitmq.queue}")
	private String hangarQueueName;
	
	@Value("${hangar.rabbitmq.routingkey}")
	private String hangarRoutingkey;
	
	@Value("${finance.rabbitmq.queue}")
	private String financeQueueName;
	
	@Value("${finance.rabbitmq.routingkey}")
	private String financeRoutingkey;

	@Bean
	DirectExchange directExchange()
	{
		return new DirectExchange(exchange);
	}
	
	@Bean
	Queue hangarQueue()
	{
		return new Queue(hangarQueueName, false);
	}
	
	@Bean
	Queue financeQueue()
	{
		return new Queue(financeQueueName, false);
	}

	@Bean
	Binding hangarBinding(Queue hangarQueue, DirectExchange directExchange)
	{
		return BindingBuilder.bind(hangarQueue).to(directExchange).with(hangarRoutingkey);
	}
	
	@Bean
	Binding financeBinding(Queue financeQueue, DirectExchange directExchange)
	{
		return BindingBuilder.bind(financeQueue).to(directExchange).with(financeRoutingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter()
	{
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory)
	{
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}