package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BookingCreateDto représente la classe DTO pour la création d'une réservation.
 *
 */
public class BookingNewDto implements Serializable
{
	@Positive(message="L'id du membre est obligatoire")
	private long memberId;
	@Positive(message="L'id de l'aéronef est obligatoire")
	private long aircraftId;
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
	
	public long getMemberId()
	{
		return memberId;
	}
	public void setMemberId(long memberId)
	{
		this.memberId = memberId;
	}
	public long getAircraftId()
	{
		return aircraftId;
	}
	public void setAircraftId(long aircraftId)
	{
		this.aircraftId = aircraftId;
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
