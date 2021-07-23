package com.ocdev.reservation.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Booking représente la classe d'une réservation.
 * @author PC_ASUS
 *
 */
@Entity
public class Booking implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private long memberId;
	@Column(nullable=false)
	private long aircraftId;
	@Column(nullable=false, length = 150)
	private String description;
	@Column(nullable=false)
	private LocalDateTime departureTime;
	@Column(nullable=false)
	private LocalDateTime arrivalTime;
	private boolean closed;
	
	public Booking()
	{
		super();
	}

	public Booking(long memberId, long aircraftId, String description, LocalDateTime departureTime, double duration)
	{
		super();
		this.memberId = memberId;
		this.aircraftId = aircraftId;
		this.description = description;
		this.departureTime = departureTime;
		int hours = (int)duration;
		int minutes = (int)((duration - hours) * 60);
		this.arrivalTime = departureTime.plusHours(hours).plusMinutes(minutes);
	}

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
