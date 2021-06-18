package com.ocdev.reservation.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
	
	@Value("${airclub.rabbitmq.queue}")
	private String airclubQueueName;
	
	@Value("${airclub.rabbitmq.routingkey}")
	private String airclubRoutingkey;
	
	@Value("${hangar.rabbitmq.queue}")
	private String hangarQueueName;
	
	@Value("${hangar.rabbitmq.routingkey}")
	private String hangarRoutingkey;
	
	@Value("${finance.rabbitmq.queue}")
	private String financeQueueName;
	
	@Value("${finance.rabbitmq.routingkey}")
	private String financeRoutingkey;

	@Bean
	TopicExchange topicExchange()
	{
		return new TopicExchange(exchange);
	}
	
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
	Queue financeQueue()
	{
		return new Queue(financeQueueName, false);
	}

	@Bean
	Binding airclubBinding(Queue airclubQueue, TopicExchange topicExchange)
	{
		return BindingBuilder.bind(airclubQueue).to(topicExchange).with(airclubRoutingkey);
	}
	
	@Bean
	Binding hangarBinding(Queue hangarQueue, TopicExchange topicExchange)
	{
		return BindingBuilder.bind(hangarQueue).to(topicExchange).with(hangarRoutingkey);
	}
	
	@Bean
	Binding financeBinding(Queue financeQueue, TopicExchange topicExchange)
	{
		return BindingBuilder.bind(financeQueue).to(topicExchange).with(financeRoutingkey);
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