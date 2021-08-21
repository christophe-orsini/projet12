package com.ocdev.airclub.dto;

import java.io.Serializable;

/**
 * InvoicePayDto représente la classe DTO pour le paiement d'une facture.
 * @author PC_ASUS
 *
 */
public class InvoicePayDto implements Serializable
{
	private double amount;
	
	public InvoicePayDto()
	{
		super();
	}

	public InvoicePayDto(double amount)
	{
		this.amount = amount;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}
}
