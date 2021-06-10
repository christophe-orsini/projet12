package com.ocdev.reservation.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * Booking représente la classe DTO pour la création d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingCreateDto implements Serializable
{
	@ApiModelProperty(position = 1, required = true, value = "Id du membre effectuant le vol", example = "1")
	@Positive(message="L'id du membre est obligatoire")
	private long memberId;
	@ApiModelProperty(position = 2, required = true, value = "Immatriculation de l'aéronef", example = "F-GAAA")
	@NotBlank(message="L'immatriculation de l'aéronef est obligatoire")
	private String aircraft;
	@ApiModelProperty(position = 3, required = true, value = "Description du vol", example = "Vol local")
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@ApiModelProperty(position = 4, required = true, value = "Date et heure du vol", example = "2021-05-23T10:30")
	@DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
	private Date departureTime;
	@ApiModelProperty(position = 5, required = true, value = "Durée du vol en décimal", example = "1.5")
	private double duration;
	
	public BookingCreateDto()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingCreateDto(long memberId, String aircraft, String description, Date departureTime, double duration)
	{
		super();
		this.memberId = memberId;
		this.aircraft = aircraft;
		this.description = description;
		this.departureTime = departureTime;
		this.duration = duration;
	}

	public long getMemberId()
	{
		return memberId;
	}

	public void setMemberId(long memberId)
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(Date departureTime)
	{
		this.departureTime = departureTime;
	}

	public double getDuration()
	{
		return duration;
	}

	public void setDuration(double duration)
	{
		this.duration = duration;
	}
}
