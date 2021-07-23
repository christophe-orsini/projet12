package com.ocdev.financial.messages;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * RegisterFlightMessage représente la classe d'un vol à enregistrer.
 * @author PC_ASUS
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = RegisterFlightMessage.class)
public class RegisterFlightMessage implements Serializable
{
	private long memberId;
	private long aircraftId;
	private String description;
	private LocalDate flightDate;
	private double duration;
	private int hourlyRate;
	
	public RegisterFlightMessage()
	{
		super();
	}

	public RegisterFlightMessage(long memberId, long aircraftId, String description, LocalDate flightDate, 
			double duration, int hourlyRate)
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

	public LocalDate getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate)
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

	@Override
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "RegisterFlightMessage [memberId=" + memberId + ", aircraftId=" + aircraftId + ", description=" + description
				+ ", flightDate=" + sdf.format(flightDate) + ", duration=" + duration + ", hourlyRate=" + hourlyRate + "]";
	}
}
