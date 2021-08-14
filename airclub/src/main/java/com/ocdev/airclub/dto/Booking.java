package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Booking représente la classe d'une réservation duservice réservation.
 * @author PC_ASUS
 *
 */
public class Booking implements Serializable
{
	private long id;
	private String memberId;
	private long aircraftId;
	private String description;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private boolean closed;
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

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

	public LocalDateTime getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}

	public boolean isClosed()
	{
		return closed;
	}

	public void setClosed(boolean closed)
	{
		this.closed = closed;
	}

	@Override
	public String toString()
	{
		return "Booking [memberId=" + memberId + ", aircraftId=" + aircraftId + ", description=" + description
				+ ", departureTime=" + departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
				+ ", arrivalTime=" + arrivalTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "]";
	}	
}
