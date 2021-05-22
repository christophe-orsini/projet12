package com.ocdev.financial.dto;

import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO utilisé pour créer une cotisation
 * @author PC_ASUS
 *
 */
@ApiModel(value = "SubscriptionDto", description = "Modèle DTO pour la création d'une cotisation")
public class SubscriptionDto
{
	@ApiModelProperty(position = 1, required = true, value = "Id du membre", example = "1")
	@Positive(message="L'id du membre est obligatoire et doit être positive")
	private long memberId;
	@ApiModelProperty(position = 2, required = false, value = "Montant de la cotisation (absent pour utiliser le forfait)",
			example = "135.0 ou absent pour utiliser le forfait")
	private Double amount;

	public long getMemberId()
	{
		return memberId;
	}
	public void setMemberId(long memberId)
	{
		this.memberId = memberId;
	}
	public double getAmount()
	{
		if (amount == null) return -1.0;
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
}
