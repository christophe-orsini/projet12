package com.ocdev.hangar.converters;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;
import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;

@Component
public class AircraftDtoConverter implements IDtoConverter<Aircraft, AircraftDto>
{
	@Override
	public Aircraft convertDtoToEntity(AircraftDto aircraftDto)
	{
		Aircraft aircraft = new Aircraft();
		
		aircraft.setRegistration(aircraftDto.getRegistration());
		aircraft.setMake(aircraftDto.getMake());
		aircraft.setModel(aircraftDto.getModel());
		aircraft.setHourlyRate(aircraftDto.getHourlyRate());
		aircraft.setTotalTime(aircraftDto.getTotalTime());
		aircraft.setEmptyWeight(aircraftDto.getEmptyWeight());
		aircraft.setMaxWeight(aircraftDto.getMaxWeight());
		aircraft.setMaxPax(aircraftDto.getMaxPax());
		aircraft.setMaxFuel(aircraftDto.getMaxFuel());
		aircraft.setFuelType(aircraftDto.getFuelType());
		
		return aircraft;
	}

	@Override
	public AircraftDto convertEntityToDto(Aircraft entity)
	{
		throw new NotYetImplementedException(); // TODO créer une exception
	}
}