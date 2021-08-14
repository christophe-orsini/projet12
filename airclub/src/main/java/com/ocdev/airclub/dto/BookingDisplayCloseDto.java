package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BookingDisplayCloseDto représente la classe DTO pour la clôture d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingDisplayCloseDto implements Serializable
{
	@Positive(message="LId de la réservation doit être positive")
	private long id;
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate departureDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime departureTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate arrivalDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime arrivalTime;
	
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public LocalDate getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate)
	{
		this.departureDate = departureDate;
	}

	public LocalTime getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime)
	{
		this.departureTime = departureTime;
	}

	public LocalDate getArrivalDate()
	{
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}
}
