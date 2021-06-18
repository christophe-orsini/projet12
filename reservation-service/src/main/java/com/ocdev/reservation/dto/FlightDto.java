package com.ocdev.reservation.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * FlightDto représente la classe d'un vol à enregistrer dans hangar et finance.
 * @author PC_ASUS
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = FlightDto.class)
public class FlightDto implements Serializable
{
	private long memberId;
	private long aircraftId;
	private String description;
	private Date flightDate;
	private double duration;
	private int hourlyRate;
	
	public FlightDto()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public FlightDto(long memberId, long aircraftId, String description, Date flightDate, double duration, int hourlyRate)
	{
		super();
		this.memberId = memberId;
		this.aircraftId = aircraftId;
		this.description = description;
		this.flightDate = flightDate;
		this.duration = duration;
		this.hourlyRate = hourlyRate;
	}

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

	public Date getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}

	public double getDuration()
	{
		return duration;
	}

	public void setDuration(double duration)
	{
		this.duration = duration;
	}

	public int getHourlyRate()
	{
		return hourlyRate;
	}

	public void setHourlyRate(int hourlyRate)
	{
		this.hourlyRate = hourlyRate;
	}
}
