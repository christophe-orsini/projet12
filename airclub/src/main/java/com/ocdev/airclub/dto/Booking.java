package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Booking représente la classe d'une réservation duservice réservation.
 * @author PC_ASUS
 *
 */
public class Booking implements Serializable
{
	private long id;
	private long memberId;
	private long aircraftId;
	private String description;
	private Date departureTime;
	private Date arrivalTime;
	private boolean closed;
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
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

	public Date getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(Date departureTime)
	{
		this.departureTime = departureTime;
	}

	public Date getArrivalTime()
	{
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime)
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return "Booking [memberId=" + memberId + ", aircraftId=" + aircraftId + ", description=" + description
				+ ", departureTime=" + sdf.format(departureTime) + ", arrivalTime=" + sdf.format(arrivalTime) + "]";
	}	
}
