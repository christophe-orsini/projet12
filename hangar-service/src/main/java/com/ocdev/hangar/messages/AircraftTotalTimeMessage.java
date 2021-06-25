package com.ocdev.hangar.messages;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * AircraftTotalTimeMessage représente le message de mise à jour du totalTime e Aircraft.
 * @author PC_ASUS
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = AircraftTotalTimeMessage.class)
public class AircraftTotalTimeMessage implements Serializable
{
	private long aircraftId;
	private double duration;
	
	public AircraftTotalTimeMessage()
	{
		super();
	}

	public AircraftTotalTimeMessage(long aircraftId, double duration)
	{
		super();
		this.aircraftId = aircraftId;
		this.duration = duration;
	}

	public long getAircraftId()
	{
		return aircraftId;
	}

	public void setAircraftId(long aircraftId)
	{
		this.aircraftId = aircraftId;
	}

	public double getDuration()
	{
		return duration;
	}

	public void setDuration(double duration)
	{
		this.duration = duration;
	}

	@Override
	public String toString()
	{
		return "AircraftTotalTimeMessage [aircraftId=" + aircraftId + ", duration=" + duration + "]";
	}
}
