package com.ocdev.financial.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/**
 * InvoicePayDto représente la classe DTO pour le paiement d'une facture.
 * @author PC_ASUS
 *
 */
public class InvoicePayDto implements Serializable
{
	@ApiModelProperty(position = 1, required = true, value = "Montant payé", example = "129.0")
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
