package com.ocdev.financial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.ocdev.financial")
@EnableDiscoveryClient
@SpringBootApplication
public class FinancialServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(FinancialServiceApplication.class, args);
	}
}
