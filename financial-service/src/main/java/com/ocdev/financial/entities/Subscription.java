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
 * Subscription représente la classe de paiement de la cotisation pour un membre.
 * @author PC_ASUS
 *
 */
@Entity
@Hidden
public class Subscription implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private long memberId;
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	@Column(nullable=false)
	private double amount;
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date validityDate;
	
	public Subscription()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscription(long memberId, Date paymentDate, double amount)
	{
		super();
		this.memberId = memberId;
		this.paymentDate = paymentDate;
		this.amount = amount;
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

	public Date getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public Date getValidityDate()
	{
		return validityDate;
	}

	public void setValidityDate(Date validityDate)
	{
		this.validityDate = validityDate;
	}

	@Override
	public String toString()
	{
		return "Subscription [memberId=" + memberId + ", paymentDate=" + paymentDate + ", amount="
				+ amount + ", validityDate=" + validityDate + "]";
	}
}
