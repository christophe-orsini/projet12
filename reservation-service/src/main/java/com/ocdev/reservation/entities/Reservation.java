package com.ocdev.reservation.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Reservation représente la classe d'une réservation.
 * @author PC_ASUS
 *
 */
@Entity
public class Reservation implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
}
