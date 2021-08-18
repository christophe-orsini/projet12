package com.ocdev.financial.converters;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.entities.Flight;

@Component
public class FlightRecordDtoConverter implements IDtoConverter<Flight, FlightRecordDto>
{
	@Override
	public Flight convertDtoToEntity(FlightRecordDto dto)
	{
		Flight entity = new Flight();
		
		entity.setMemberId(dto.getMemberId());
		entity.setAircraft(dto.getAircraft());
		entity.setLineItem(dto.getLineItem());
		entity.setFlightDate(dto.getFlightDate());
		double duration = dto.getFlightHours();
		duration = Math.round(duration * 10.0) / 10.0;
		entity.setFlightHours(duration);
		entity.setAmount(dto.getAmount());
		
		return entity;
	}

	@Override
	public FlightRecordDto convertEntityToDto(Flight entity)
	{
		throw new NotYetImplementedException();
	}
}