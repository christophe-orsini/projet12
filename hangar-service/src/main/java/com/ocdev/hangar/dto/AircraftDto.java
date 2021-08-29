package com.ocdev.hangar.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO utilisé pour créer un aéronef
 * @author PC_ASUS
 *
 */
@ApiModel(value = "AircraftDto", description = "Modèle DTO pour la création/modification d'un aéronef")
public class AircraftDto
{
	@ApiModelProperty(position = 1, required = true, value = "Immatriculation de l'aéronef", example = "F-GHNY")
	@NotBlank(message="L'immatriculation est obligatoire")
	private String registration;
	@ApiModelProperty(position = 2, required = true, value = "Marque de l'aéronef")
	@NotBlank(message="La marque est obligatoire")
	private String make;
	@ApiModelProperty(position = 3, required = true, value = "Modèle de l'aéronef")
	@NotBlank(message="Le modèle est obligatoire")
	private String model;
	@ApiModelProperty(position = 4, required = true, value = "Tarif horaire")
	@PositiveOrZero(message="Le tarif horaire doit être positif (ou zéro si inconnue)")
	private int hourlyRate;
	@ApiModelProperty(position = 5, required = true, value = "Nombre d'heures totales depuis neuf")
	@PositiveOrZero(message="Le nombre d'heures totales depuis neuf doit être positif (ou zéro si inconnu)")
	private double totalTime;
	@ApiModelProperty(position = 6, required = true, value = "Masse à vide de l'aéronef")
	@PositiveOrZero(message="La masse de l'aéronef doit être positive (ou zéro si inconnue)")
	private int emptyWeight;
	@ApiModelProperty(position = 7, required = true, value = "Masse maximale au décollage")
	@PositiveOrZero(message="La masse maximale au décollage doit être positive (ou zéro si inconnue)")
	private int maxWeight;
	@ApiModelProperty(position = 8, required = true, value = "Nombre maximum de personnes à bord")
	@PositiveOrZero(message="Le nombre maximum de personnes à bord être positif (minimum 1 pilote)")
	private int maxPax;
	@ApiModelProperty(position = 9, required = true, value = "Capacité maximale de carburant")
	@PositiveOrZero(message="La capacité maximale de carburant doit être positive (ou zéro si inconnue)")
	private int maxFuel;
	@ApiModelProperty(position = 10, required = true, value = "Type de carburant")
	private String fuelType;
	
	public String getRegistration()
	{
		return registration;
	}
	public void setRegistration(String registration)
	{
		this.registration = registration;
	}
	public String getMake()
	{
		return make;
	}
	public void setMake(String make)
	{
		this.make = make;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public int getHourlyRate()
	{
		return hourlyRate;
	}
	public void setHourlyRate(int hourlyRate)
	{
		this.hourlyRate = hourlyRate;
	}
	public double getTotalTime()
	{
		return totalTime;
	}
	public void setTotalTime(double totalTime)
	{
		this.totalTime = totalTime;
	}
	public int getEmptyWeight()
	{
		return emptyWeight;
	}
	public void setEmptyWeight(int emptyWeight)
	{
		this.emptyWeight = emptyWeight;
	}
	public int getMaxWeight()
	{
		return maxWeight;
	}
	public void setMaxWeight(int maxWeight)
	{
		this.maxWeight = maxWeight;
	}
	public int getMaxPax()
	{
		return maxPax;
	}
	public void setMaxPax(int maxPax)
	{
		this.maxPax = maxPax;
	}
	public int getMaxFuel()
	{
		return maxFuel;
	}
	public void setMaxFuel(int maxFuel)
	{
		this.maxFuel = maxFuel;
	}
	public String getFuelType()
	{
		return fuelType;
	}
	public void setFuelType(String fuelType)
	{
		this.fuelType = fuelType;
	}
}
