package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


/**
 * BookingCreateDto représente la classe DTO pour la création d'une réservation.
 *
 */
public class BookingCreateDto implements Serializable
{
	@NotBlank(message="L'id du membre est obligatoire")
	private String memberId;
	@Positive(message="L'id de l'aéronef est obligatoire")
	private long aircraftId;
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	private LocalDateTime departureTime;
	@Positive(message="La durée du vol est obligatoire")
	private double duration;
	
	public String getMemberId()
	{
		return memberId;
	}
	public void setMemberId(String memberId)
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
