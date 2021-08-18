package com.ocdev.reservation.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * BookingCloseDto représente la classe DTO pour la clôture d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingCloseDto implements Serializable
{
	private String givenName;
	private String familyName;
	private String email;
	@ApiModelProperty(position = 1, required = true, value = "Description du vol", example = "Vol local")
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@ApiModelProperty(position = 2, required = true, value = "Date et heure du départ", example = "2021-05-23T10:30")
	@DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
	private LocalDateTime departureTime;
	@ApiModelProperty(position = 3, required = true, value = "Durée du vol en décimal", example = "1.5")
	private double duration;
	
	public BookingCloseDto()
	{
		super();
	}

	public BookingCloseDto(@NotBlank(message = "La description du vol est obligatoire") String description, LocalDateTime departureTime,	double duration)
	{
		super();
		this.description = description;
		this.departureTime = departureTime;
		this.duration = duration;
	}

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public LocalDateTime getDepartureTime()
	{
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime)
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
