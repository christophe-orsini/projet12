package com.ocdev.airclub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirclubApplication implements CommandLineRunner 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AirclubApplication.class, args);	
    }

	@Override
	public void run(String... args) throws Exception
	{
		
	}
}
