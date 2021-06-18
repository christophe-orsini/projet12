package com.ocdev.reservation.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * BookingCloseDto représente la classe DTO pour la clôture d'une réservation.
 * @author PC_ASUS
 *
 */
public class BookingCloseDto implements Serializable
{
	@ApiModelProperty(position = 1, required = true, value = "Id de la réservation", example = "1")
	@Positive(message="L'id de la réservation est obligatoire")
	private long reservationId;
	@ApiModelProperty(position = 2, required = true, value = "Description du vol", example = "Vol local")
	@NotBlank(message="La description du vol est obligatoire")
	private String description;
	@ApiModelProperty(position = 3, required = true, value = "Date et heure du départ", example = "2021-05-23T10:30")
	@DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
	private Date departureTime;
	@ApiModelProperty(position = 4, required = true, value = "Durée du vol en décimal", example = "1.5")
	private double duration;
	
	public BookingCloseDto()
	{
		super();
	}

	public BookingCloseDto(@Positive(message = "L'id de la réservation est obligatoire") long reservationId,
			@NotBlank(message = "La description du vol est obligatoire") String description, Date departureTime,
			double duration)
	{
		super();
		this.reservationId = reservationId;
		this.description = description;
		this.departureTime = departureTime;
		this.duration = duration;
	}

	public long getReservationId()
	{
		return reservationId;
	}

	public void setReservationId(long reservationId)
	{
		this.reservationId = reservationId;
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
