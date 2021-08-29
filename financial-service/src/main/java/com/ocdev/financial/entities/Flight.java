package com.ocdev.financial.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Flight représente la classe d'un vol.
 * @author PC_ASUS
 *
 */
@Entity
public class Flight implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false, length=64)
	private String memberId;
	@Column(nullable=false, length=100)
	private String aircraft;
	@Column(nullable=false, length=100)
	private String lineItem;
	@Column(nullable=false)
	private LocalDate flightDate;
	@Column(nullable=false)
	private double flightHours;
	@Column(nullable=false)
	private double amount;
	private LocalDate paymentDate;
	private double payment;
	private boolean closed;
	
	public Flight()
	{
		super();
	}

	public Flight(String memberId, String lineItem, LocalDate flightDate, double flightHours)
	{
		super();
		this.memberId = memberId;
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

	public String getLineItem()
	{
		return lineItem;
	}

	public void setLineItem(String lineItem)
	{
		this.lineItem = lineItem;
	}

	public LocalDate getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate)
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

	public LocalDate getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate)
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
				+ flightDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ", flightHours=" + flightHours + "]";
	}
}
