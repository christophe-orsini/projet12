package com.ocdev.financial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ocdev.financial.errors.CustomErrorDecoder;

@Configuration
public class FeignConfig
{
	@Bean
    public CustomErrorDecoder customErrorDecoder()
	{
        return new CustomErrorDecoder();
    }
}
