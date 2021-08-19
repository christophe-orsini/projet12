package com.ocdev.financial.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * InvoicePayDto représente la classe DTO pour le paiement d'une facture.
 * @author PC_ASUS
 *
 */
public class InvoicePayDto implements Serializable
{
	@ApiModelProperty(position = 1, required = true, value = "Numéro de la facture", example = "1")
	@Positive(message="Le numéro de la facture est obligatoire")
	private long invoiceNumber;
	@ApiModelProperty(position = 2, required = true, value = "Date de paiement de la facture", example = "2021-05-23")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paymentDate;
	@ApiModelProperty(position = 3, required = true, value = "Montant payé", example = "129.0")
	private double amount;
	
	public InvoicePayDto()
	{
		super();
	}

	public InvoicePayDto(long invoiceNumber, LocalDate paymentDate, double amount)
	{
		super();
		this.invoiceNumber = invoiceNumber;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public long getInvoiceNumber()
	{
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
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
}
