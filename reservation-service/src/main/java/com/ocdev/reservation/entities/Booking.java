package com.ocdev.reservation.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;
	
	public Booking()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(long memberId, long aircraftId, String description, Date departureTime, double duration)
	{
		super();
		this.memberId = memberId;
		this.aircraftId = aircraftId;
		this.description = description;
		this.departureTime = departureTime;
		int hours = (int)duration;
		int minutes = (int)((duration - hours) * 60);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(departureTime);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
		this.arrivalTime = calendar.getTime();
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
}
