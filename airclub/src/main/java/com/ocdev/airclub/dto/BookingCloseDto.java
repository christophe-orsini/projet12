package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BookingCloseDto représente la classe DTO pour la clôture d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingCloseDto implements Serializable
{
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime departureTime;
	@Positive(message="La durée doit être positive")
	private double duration;
	
	public BookingCloseDto()
	{
		super();
	}

	public BookingCloseDto(String description, LocalDateTime departureTime, double duration)
	{
		super();
		this.description = description;
		this.departureTime = departureTime;
		this.duration = duration;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public LocalDateTime getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime)
	{
		this.departureTime = departureTime;
	}

	public double getDuration()
	{
		return duration;
	}

	public void setDuration(double duration)
	{
		this.duration = duration;
	}
}
