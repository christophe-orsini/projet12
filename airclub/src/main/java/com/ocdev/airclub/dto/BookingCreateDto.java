package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * BookingCreateDto représente la classe DTO pour la création d'une réservation.
 *
 */
public class BookingCreateDto implements Serializable
{
	@Positive(message="L'id du membre est obligatoire")
	private long memberId;
	@Positive(message="L'id de l'aéronef est obligatoire")
	private long aircraftId;
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@DateTimeFormat(pattern = "HH:mm")
	private Date departureTime;
	@Positive(message="La durée du vol est obligatoire")
	private double duration;
	
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
	public Date getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(Date departureTime)
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
