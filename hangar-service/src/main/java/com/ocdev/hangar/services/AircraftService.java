package com.ocdev.hangar.services;

import java.util.Collection;

import com.ocdev.hangar.dto.AircraftDto;
import com.ocdev.hangar.entities.Aircraft;
import com.ocdev.hangar.errors.AlreadyExistsException;
import com.ocdev.hangar.errors.EntityNotFoundException;

public interface AircraftService
{
	/**
	 * Cette méthode crée un aéronef.
	 * 
	 * @param aircraftDto : DTO ({@link com.ocdev.hangar.dto.AircraftDto}) reçu par le endpoint
	 * @return : Le nouvel aéronef créé
	 * @throws AlreadyExistsException levée si l'aéronef existe déjà 
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 * @See {@link com.ocdev.hangar.dto.AircraftDto}
	 */
	public Aircraft create(AircraftDto aircraftDto) throws AlreadyExistsException;
	/**
	 * Cette méthode retrouve un aéronef ({@link com.ocdev.hangar.entities.Aircraft}) à partir de son id.
	 * 
	 * @param id : Id de l'aéronef
	 * @return L'aéronef trouvé
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Aircraft getById(long id) throws EntityNotFoundException;
	/**
	 * Cette méthode retrouve un aéronef ({@link com.ocdev.hangar.entities.Aircraft}) à partir de son immatriculation.
	 * 
	 * @param registration : Immatriculation de l'aéronef
	 * @return L'aéronef trouvé
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Aircraft getByRegistration(String registration) throws EntityNotFoundException;
	/**
	 * Cette méthode retourne la liste de tous les aéronefs.
	 * 
	 * @return : La liste (peut être vide)
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Collection<Aircraft> getAll();
	/***
	 * Cette méthode change le tarif horaire de location d'un aéronef.
	 * 
	 * @param registration : Immatriculation de l'aéronef
	 * @param newPrice : Le nouveau tarif horaire
	 * @return L'aéronef avec le nouveau tarif
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Aircraft updatePrice(String registration, int newPrice) throws EntityNotFoundException;
	/**
	 * Cette méthode modifie les données d'un aéronef.	
	 * 
	 * <p>Ne modifie pas le tarif horaire et la disponibilité.</p>
	 * @param aircraftDto : DTO reçu par le endpoint
	 * @return L'aéronef modifié
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 * @See {@link com.ocdev.hangar.dto.AircraftDto}
	 */
	public Aircraft updateDatas(AircraftDto aircraftDto) throws EntityNotFoundException;
	/**
	 * Cette méthode change la disponibilité d'un aéronef.
	 * 
	 * @param registration : Immatriculation de l'aéronef
	 * @param available : true pour disponible, false pour indisponible 
	 * @return L'aéronef modifié avec la nouvelle disponibilité
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Aircraft changeAvailability(String registration, boolean available) throws EntityNotFoundException;
	/**
	 * Cette méthode enregistre la nouvelle maintenance programmée.
	 * 
	 * <p>La nouvelle maintenance programmée est en terme de totalTime au moment de l'acte de maintenance<br />
	 * à laquelle il faut ajouter la durée avant la prochaine maintenance.</p>
	 * @param registration : Immatriculation de l'aéronef
	 * @param timeAdded : Nombre d'heure à ajouter pour la prochaine maintenance
	 * @return L'aéronef modifié avec la nouvelle heure de maintenance programmée
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @See {@link com.ocdev.hangar.entities.Aircraft}
	 */
	public Aircraft registerNextMaintenanceSchedule(String registration, float timeAdded) throws EntityNotFoundException;
	/**
	 * Cette méthode retourne le potentiel d'un aéronef.
	 * 
	 * <p>Le potentiel correspond au nombre d'heure restante avant la prochaine visite technique.</p>
	 * 
	 * @param registration : : Immatriculation de l'aéronef
	 * @return Le potentiel
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 */
	public double getLeftTime(String registration) throws EntityNotFoundException;
}
