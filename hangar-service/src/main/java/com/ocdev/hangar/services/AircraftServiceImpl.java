package com.ocdev.hangar.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ocdev.hangar.converters.IDtoConverter;
import com.ocdev.hangar.dao.AircraftRepository;
import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;
import com.ocdev.hangar.errors.AlreadyExistsException;
import com.ocdev.hangar.errors.EntityNotFoundException;

@Service
public class AircraftServiceImpl implements AircraftService
{
	private AircraftRepository _aircraftRepository;
	private IDtoConverter<Aircraft, AircraftDto> _aircraftCreateDtoConverter;

	public AircraftServiceImpl(@Autowired AircraftRepository repository, @Autowired IDtoConverter<Aircraft, AircraftDto> converter)
	{
		_aircraftRepository = repository;
		_aircraftCreateDtoConverter = converter;
	}
	
	@Override
	public Aircraft create(AircraftDto aircraftDto) throws AlreadyExistsException
	{
		Optional<Aircraft> aircraftExists = _aircraftRepository.findByRegistration(aircraftDto.getRegistration());
		if (aircraftExists.isPresent()) throw new AlreadyExistsException("Un aéronef avec la même immatriculation existe déjà");
		
		Aircraft aircraft = _aircraftCreateDtoConverter.convertDtoToEntity(aircraftDto);
		
		return _aircraftRepository.save(aircraft);
	}

	@Override
	public Aircraft getById(long id) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findById(id);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		return aircraft.get();
	}
	@Override
	public Aircraft getByRegistration(String registration) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(registration);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		return aircraft.get();
	}

	@Override
	public Collection<Aircraft> getAll()
	{
		return _aircraftRepository.findAll();
	}
	
	@Override
	public Aircraft updatePrice(String registration, int newPrice) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(registration);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		aircraft.get().setHourlyRate(newPrice);
		
		return _aircraftRepository.save(aircraft.get());
	}

	@Override
	public Aircraft updateDatas(AircraftDto aircraftDto) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(aircraftDto.getRegistration());
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		Aircraft newAircraft = _aircraftCreateDtoConverter.convertDtoToEntity(aircraftDto);
		newAircraft.setId(aircraft.get().getId());
		newAircraft.setHourlyRate(aircraft.get().getHourlyRate()); // no change for hourlyRate with this methode @see updatePrice
		newAircraft.setAvailable(aircraft.get().isAvailable()); // no change for available with this methode @see changeAvailability
		
		return _aircraftRepository.save(newAircraft);
	}

	@Override
	public Aircraft changeAvailability(String registration, boolean available) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(registration);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		aircraft.get().setAvailable(available);
		
		return _aircraftRepository.save(aircraft.get());
	}
	
	@Override
	public Aircraft registerNextMaintenanceSchedule(String registration, float timeAdded)	throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(registration);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		aircraft.get().setNextMaintenanceSchedule(aircraft.get().getTotalTime() + timeAdded);
		
		return _aircraftRepository.save(aircraft.get());
	}
	
	@Override
	public double getLeftTime(String registration) throws EntityNotFoundException
	{
		Optional<Aircraft> aircraft = _aircraftRepository.findByRegistration(registration);
		if (!aircraft.isPresent()) throw new EntityNotFoundException("Cet aéronef n'existe pas");
		
		return aircraft.get().getNextMaintenanceSchedule() - aircraft.get().getTotalTime();
	}
}
