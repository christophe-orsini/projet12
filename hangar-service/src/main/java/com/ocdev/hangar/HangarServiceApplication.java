package com.ocdev.hangar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HangarServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(HangarServiceApplication.class, args);
	}
}
