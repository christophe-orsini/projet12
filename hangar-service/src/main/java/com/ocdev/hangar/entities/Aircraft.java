package com.ocdev.hangar.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Aircraft représente la classe d'un aéronef.
 * @author PC_ASUS
 *
 */
@Entity
@Hidden
public class Aircraft implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false, length=10, unique = true)
	private String registration;
	@Column(nullable=false, length=50)
	private String make;
	@Column(nullable=false, length=50)
	private String model;
	private int hourlyRate;
	private double totalTime;
	private double nextMaintenanceSchedule;
	private int emptyWeight;
	private int maxWeight;
	private int maxPax;
	private int maxFuel;
	private String fuelType;
	private boolean available;

	public Aircraft()
	{
		super();
	}

	public Aircraft(String registration, String make, String model)
	{
		super();
		this.registration = registration;
		this.make = make;
		this.model = model;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

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

	public double getNextMaintenanceSchedule()
	{
		return nextMaintenanceSchedule;
	}

	public void setNextMaintenanceSchedule(double nextMaintenanceSchedule)
	{
		this.nextMaintenanceSchedule = nextMaintenanceSchedule;
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

	public boolean isAvailable()
	{
		return available;
	}

	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	@Override
	public String toString()
	{
		return "Aircraft [registration=" + registration + ", make=" + make + ", model=" + model + "]";
	}
}
