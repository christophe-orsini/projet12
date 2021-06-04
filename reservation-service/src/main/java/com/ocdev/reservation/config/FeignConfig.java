package com.ocdev.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ocdev.reservation.errors.CustomErrorDecoder;

@Configuration
public class FeignConfig
{
	@Bean
    public CustomErrorDecoder customErrorDecoder()
	{
        return new CustomErrorDecoder();
    }
}
