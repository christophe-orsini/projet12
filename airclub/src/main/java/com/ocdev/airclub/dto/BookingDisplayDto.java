package com.ocdev.airclub.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BookingDisplay représente la classe DTO pour la visualisation d'une réservation.
 *
 */
public class BookingDisplayDto implements Serializable
{
	private long id;
	private String memberId;
	private String aircraft;
	private String description;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private boolean closed;
	private boolean canCancel;
	
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
	public String getAircraft()
	{
		return aircraft;
	}
	public void setAircraft(String aircraft)
	{
		this.aircraft = aircraft;
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
	public boolean isCanCancel()
	{
		return canCancel;
	}
	public void setCanCancel(boolean canCancel)
	{
		this.canCancel = canCancel;
	}
}
