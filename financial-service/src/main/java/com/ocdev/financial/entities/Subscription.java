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
 * Subscription représente la classe de paiement de la cotisation pour un membre.
 * @author PC_ASUS
 *
 */
@Entity
public class Subscription implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false, length=64)
	private String memberId;
	@Column(nullable=false)
	private LocalDate paymentDate;
	@Column(nullable=false)
	private double amount;
	@Column(nullable=false)
	private LocalDate validityDate;
	
	public Subscription()
	{
		super();
	}

	public Subscription(String memberId, LocalDate paymentDate, double amount)
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

	public String getMemberId()
	{
		return memberId;
	}

	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}

	public LocalDate getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate)
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

	public LocalDate getValidityDate()
	{
		return validityDate;
	}

	public void setValidityDate(LocalDate validityDate)
	{
		this.validityDate = validityDate;
	}

	@Override
	public String toString()
	{
				return "Subscription [memberId=" + memberId + ", paymentDate=" + paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				+ ", amount=" + amount + ", validityDate=" + validityDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "]";
	}
}
