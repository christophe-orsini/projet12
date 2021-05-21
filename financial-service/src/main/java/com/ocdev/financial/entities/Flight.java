package com.ocdev.financial.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Flight représente la classe d'un vol.
 * @author PC_ASUS
 *
 */
@Entity
@Hidden
public class Flight implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private long memberId;
	@Column(nullable=false, length=100)
	private String aircraft;
	@Column(nullable=false, length=100)
	private String lineItem;
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date flightDate;
	@Column(nullable=false)
	private double flightHours;
	@Column(nullable=false)
	private double amount;
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	private double payment;
	private boolean closed;
	
	public Flight()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Flight(long memberId, String aircraft, String lineItem, Date flightDate, double flightHours)
	{
		super();
		this.memberId = memberId;
		this.aircraft= aircraft;
		this.lineItem = lineItem;
		this.flightDate = flightDate;
		this.flightHours = flightHours;
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

	public String getAircraft()
	{
		return aircraft;
	}

	public void setAircraft(String aircraft)
	{
		this.aircraft = aircraft;
	}

	public String getLineItem()
	{
		return lineItem;
	}

	public void setLineItem(String lineItem)
	{
		this.lineItem = lineItem;
	}

	public Date getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}

	public double getFlightHours()
	{
		return flightHours;
	}

	public void setFlightHours(double flightHours)
	{
		this.flightHours = flightHours;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public Date getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public double getPayment()
	{
		return payment;
	}

	public void setPayment(double payment)
	{
		this.payment = payment;
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
		return "Flight [memberId=" + memberId + ", aircraft=" + aircraft + ", lineItem=" + lineItem + ", flightDate="
				+ flightDate + ", flightHours=" + flightHours + "]";
	}
}
