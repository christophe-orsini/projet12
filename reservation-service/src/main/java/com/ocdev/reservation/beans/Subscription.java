package com.ocdev.reservation.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Subscription représente la classe de paiement de la cotisation pour un membre.
 * @author PC_ASUS
 *
 */
public class Subscription implements Serializable
{
	private long id;
	private String memberId;
	private Date paymentDate;
	private double amount;
	private Date validityDate;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "Subscription [memberId=" + memberId + ", paymentDate=" + sdf.format(paymentDate) + ", amount="
				+ amount + ", validityDate=" + sdf.format(validityDate) + "]";
	}
}
