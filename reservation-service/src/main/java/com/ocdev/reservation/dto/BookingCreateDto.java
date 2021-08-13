package com.ocdev.reservation.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * BookingCreateDto représente la classe DTO pour la création d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingCreateDto implements Serializable
{
	@ApiModelProperty(position = 1, required = true, value = "Id du membre effectuant le vol", example = "5ffe-4d55")
	@NotBlank(message="L'id du membre est obligatoire")
	private String memberId;
	@ApiModelProperty(position = 2, required = true, value = "Id de l'aéronef", example = "1")
	@Positive(message="L'immatriculation de l'aéronef est obligatoire")
	private long aircraftId;
	@ApiModelProperty(position = 3, required = true, value = "Description du vol", example = "Vol local")
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@ApiModelProperty(position = 4, required = true, value = "Date et heure du vol", example = "2021-05-23T10:30")
	@DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
	private LocalDateTime departureTime;
	@ApiModelProperty(position = 5, required = true, value = "Durée du vol en décimal", example = "1.5")
	private double duration;
	
	public BookingCreateDto()
	{
		super();
	}

	public BookingCreateDto(String memberId, long aircraftId, String description, LocalDateTime departureTime, double duration)
	{
		super();
		this.memberId = memberId;
		this.aircraftId = aircraftId;
		this.description = description;
		this.departureTime = departureTime;
		this.duration = duration;
	}

	public String getMemberId()
	{
		return memberId;
	}

	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}

	public long getAircraftId()
	{
		return aircraftId;
	}

	public void setAircraftId(long aircraftId)
	{
		this.aircraftId = aircraftId;
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
