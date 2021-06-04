package com.ocdev.reservation.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Booking représente la classe d'une réservation.
 * @author PC_ASUS
 *
 */
@Entity
public class Booking implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private long memberId;
	@Column(nullable=false)
	private long aircraftId;
	@Column(nullable=false, length = 150)
	private String description;
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;
	
	
}
