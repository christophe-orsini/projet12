package com.ocdev.hangar.services;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ocdev.hangar.converters.IDtoConverter;
import com.ocdev.hangar.dao.AircraftRepository;
import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;
import com.ocdev.hangar.errors.AlreadyExistsException;
import com.ocdev.hangar.errors.EntityNotFoundException;
import com.ocdev.hangar.messages.AircraftTotalTimeMessage;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceImplTest
{
	private AircraftServiceImpl systemUnderTest;
	
	private AutoCloseable _closeable;
	@Mock private AircraftRepository _aircraftRepositoryMock;
	@Mock private IDtoConverter<Aircraft, AircraftDto> _aircraftDtoConverterMock;
	
	@BeforeEach
	void setUp() throws Exception
	{
		 _closeable = MockitoAnnotations.openMocks(this);
		 systemUnderTest = new AircraftServiceImpl(_aircraftRepositoryMock, _aircraftDtoConverterMock);
	}
	
	@AfterEach public void releaseMocks() throws Exception
	{
        _closeable.close();
    }
	
	@Test
	void create_ShouldRaiseAlreadyExistsException_WhenAircraftExists()
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		
		AircraftDto aircraftDto = new AircraftDto();
		aircraftDto.setRegistration("F-GHNY");
		
		// act & assert
		assertThatExceptionOfType(AlreadyExistsException.class).isThrownBy(() ->
		{
			systemUnderTest.create(aircraftDto);
		}).withMessage("Un aéronef avec la même immatriculation existe déjà");
	}
	
	@Test
	void create_ShouldReturnNewAircraft_WhenAircraftNotExists() throws AlreadyExistsException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(_aircraftDtoConverterMock.convertDtoToEntity(Mockito.any(AircraftDto.class))).thenReturn(aircraft);
		Mockito.when(_aircraftRepositoryMock.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
		
		AircraftDto aircraftDto = new AircraftDto();
		aircraftDto.setRegistration("F-GHNY");
		
		// act
		Aircraft actual = systemUnderTest.create(aircraftDto);
		
		// assert
		assertThat(actual.getRegistration()).isEqualTo(aircraftDto.getRegistration());
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findByRegistration(aircraftDto.getRegistration());
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}
	
	@Test
	void getByRegistration_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.getByRegistration("F-GCNS");
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void getByRegistration_ShouldReturnAircraft_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		
		// act
		Aircraft actual = systemUnderTest.getByRegistration("F-GHNY");
		
		// assert
		assertThat(actual.getRegistration()).isEqualTo(aircraft.getRegistration());
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findByRegistration(aircraft.getRegistration());
	}
	
	@Test
	void getById_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.getById(9);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void getById_ShouldReturnAircraft_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setId(9);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(aircraft));
		
		// act
		Aircraft actual = systemUnderTest.getById(9);
		
		// assert
		assertThat(actual.getId()).isEqualTo(aircraft.getId());
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findById(aircraft.getId());
	}
	
	@Test
	void getAll_ShouldReturnEmpty_WhenNoAircrafts()
	{
		// arrange
		Mockito.<Collection<Aircraft>>when(_aircraftRepositoryMock.findAll()).thenReturn(new ArrayList<Aircraft>());
		
		// act
		Collection<Aircraft> actual = systemUnderTest.getAll();
		
		// assert
		assertThat(actual.size()).isEqualTo(0);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findAll();
	}
	
	@Test
	void getAll_ShouldReturnAircrafts_WhenNoAircrafts()
	{
		// arrange
		Collection<Aircraft> expected = new ArrayList<Aircraft>();
		expected.add(new Aircraft());
		Mockito.<Collection<Aircraft>>when(_aircraftRepositoryMock.findAll()).thenReturn(expected);
		
		// act
		Collection<Aircraft> actuals = systemUnderTest.getAll();
		
		// assert
		assertThat(actuals.size()).isEqualTo(1);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findAll();
	}
		
	@Test
	void updatePrice_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.updatePrice("F-GCNS", 149);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void updatePrice_ShouldReturnNewPrice_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		Mockito.when(_aircraftRepositoryMock.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
		
		// act
		Aircraft actual = systemUnderTest.updatePrice("F-GHNY", 149);
		
		// assert
		assertThat(actual.getHourlyRate()).isEqualTo(149);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}
	
	@Test
	void updateDatas_ShouldRaiseEntityNotFoundException_WhenAircraftNotExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		AircraftDto aircraftDto = new AircraftDto();
		aircraftDto.setRegistration("F-GHNY");
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.updateDatas(aircraftDto);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void updateDatas_ShouldReturnNewEmptyWeight_WhenAircraftExists() throws EntityNotFoundException 
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		aircraft.setHourlyRate(129);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		Mockito.when(_aircraftDtoConverterMock.convertDtoToEntity(Mockito.any(AircraftDto.class))).thenReturn(aircraft);
		Mockito.when(_aircraftRepositoryMock.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
		
		AircraftDto aircraftDto = new AircraftDto();
		aircraftDto.setRegistration("F-GHNY");
		
		// act
		Aircraft actual = systemUnderTest.updateDatas(aircraftDto);
		
		// assert
		assertThat(actual.getRegistration()).isEqualTo(aircraftDto.getRegistration());
		assertThat(actual.getHourlyRate()).isEqualTo(aircraft.getHourlyRate());
		Mockito.verify(_aircraftDtoConverterMock, Mockito.times(1)).convertDtoToEntity(aircraftDto);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findByRegistration(aircraftDto.getRegistration());
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}

	@Test
	void changeAvailability_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.changeAvailability("F-GCNS", true);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void changeAvailability_ShouldReturnNewAvailability_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		aircraft.setAvailable(false);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		Mockito.when(_aircraftRepositoryMock.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
		
		// act
		Aircraft actual = systemUnderTest.changeAvailability("F-GHNY", true);
		
		// assert
		assertThat(actual.isAvailable()).isTrue();
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}
	
	@Test
	void registerNextMaintenanceSchedule_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.registerNextMaintenanceSchedule("F-GCNS", 12345.6f);
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void registerNextMaintenanceSchedule_ShouldReturnNewTime_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		aircraft.setTotalTime(12345.6f);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		Mockito.when(_aircraftRepositoryMock.save(Mockito.any(Aircraft.class))).thenReturn(aircraft);
		
		// act
		Aircraft actual = systemUnderTest.registerNextMaintenanceSchedule("F-GHNY", 100f);
		
		// assert
		assertThat(actual.getNextMaintenanceSchedule()).isEqualTo(12445.6f);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}
	
	@Test
	void getLeftTime_ShouldRaiseEntityNotFoundException_WhenNotAircraftExists()
	{
		//arrange
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.empty());
		
		// act & assert
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
		{
			systemUnderTest.getLeftTime("F-GCNS");
		}).withMessage("Cet aéronef n'existe pas");
	}
	
	@Test
	void getLeftTime_ShouldReturnNewTime_WhenAircraftExists() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		aircraft.setTotalTime(12345.6f);
		aircraft.setNextMaintenanceSchedule(12365.6f);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		
		// act
		double actual = systemUnderTest.getLeftTime("F-GHNY");
		
		// assert
		assertThat(actual).isEqualTo(20f);
	}
	
	@Test
	void getLeftTime_ShouldReturnNegativeTime_WhenScheduleHasPassed() throws EntityNotFoundException
	{
		//arrange
		Aircraft aircraft = new Aircraft();
		aircraft.setRegistration("F-GHNY");
		aircraft.setTotalTime(12345.6f);
		aircraft.setNextMaintenanceSchedule(12325.6f);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findByRegistration(Mockito.anyString())).thenReturn(Optional.of(aircraft));
		
		// act
		double actual = systemUnderTest.getLeftTime("F-GHNY");
		
		// assert
		assertThat(actual).isEqualTo(-20f);
	}
	
	@Test
	void updateFlightHours_ShouldDoNothing_WhenAircraftNotExists()
	{
		//arrange
		AircraftTotalTimeMessage message = new AircraftTotalTimeMessage(9L,  2.0);
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// act
		systemUnderTest.updateFlightHours(message);
		
		// assert
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findById(9L);
		Mockito.verify(_aircraftRepositoryMock, Mockito.never()).save(Mockito.any(Aircraft.class));
	}
	
	@Test
	void updateFlightHours_ShouldUpdate_WhenAircraftExists()
	{
		//arrange
		AircraftTotalTimeMessage message = new AircraftTotalTimeMessage(9L,  2.0);
		Aircraft aircraft = new Aircraft();
		Mockito.<Optional<Aircraft>>when(_aircraftRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(aircraft));
		
		// act
		systemUnderTest.updateFlightHours(message);
		
		// assert
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).findById(9L);
		Mockito.verify(_aircraftRepositoryMock, Mockito.times(1)).save(aircraft);
	}
}
