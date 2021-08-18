package com.ocdev.financial.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO utilisé pour créer un vol
 * @author PC_ASUS
 *
 */
@ApiModel(value = "FlightRecordDto", description = "Modèle DTO pour la création d'un vol")
public class FlightRecordDto
{
	@ApiModelProperty(position = 1, required = true, value = "Id du membre effectuant le vol", example = "2ffe-5a55")
	@NotBlank(message="L'id du membre est obligatoire")
	private String memberId;
	@ApiModelProperty(position = 2, required = true, value = "Immatriculation, marque et modèle de l'aéronef", example = "F-GCNS CESSNA C152")
	@NotBlank(message="L'aéronef est obligatoire")
	private String aircraft;
	@ApiModelProperty(position = 3, required = true, value = "Description du vol", example = "Vol local")
	@NotBlank(message="La description du vol est obligatoire")
	private String lineItem;
	@ApiModelProperty(position = 4, required = true, value = "Date du vol", example = "2021-05-23")
	@PastOrPresent(message = "La date du vol ne peut pas être posterieure à aujourd'hui")
	private LocalDate flightDate;
	@ApiModelProperty(position = 5, required = true, value = "Durée du vol en décimal", example = "1.3")
	@Positive(message="La durée du vol doit être supérieure à zéro)")
	private double flightHours;
	@ApiModelProperty(position = 6, required = true, value = "Coût du vol", example = "123.45")
	@PositiveOrZero(message="Le coût du vol doit être supérieure ou égal à zéro)")
	private double amount;
	
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
}
