package com.ocdev.reservation.services;

import java.util.Collection;
import java.util.Date;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.errors.ProxyException;

public interface ReservationService
{
	/**
	 * Cette vérifie si un aéronef est disponible à une heure donnée et pour une durée determinée.
	 * <p>Un aéronef est disponible s'il n'est pas déjà réservé pour la période 
	 * et s'il n'est pas techniquement indisponible</p>
	 * 
	 * @param registration : Immatriculation de l'aéronef
	 * @param startTime : Date et heure du départ prévue
	 * @param duration: Date et heure du départ prévue
	 * @return : true si l'aéronef est disponible, false sinon
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas 
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 */
	public boolean isAircaftAvailable(String registration, Date startTime, double duration) throws EntityNotFoundException, ProxyException;
	/**
	 * Cette méthode retourne la liste des aéronefs ({@link Aircraft}) disponible à une heure donnée et pour une durée déterminée.
	 * <p>Un aéronef est disponible s'il n'est pas déjà réservé pour la période 
	 * et s'il n'est pas techniquement indisponible</p>
	 * 
	 * @param startTime : Date et heure du départ prévue
	 * @param duration : Date et heure du départ prévue
	 * @return :La liste des aéronefs disponibles
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 * @see Aircraft
	 */
	public Collection<Aircraft> availableAircrafts(Date startTime, double duration) throws ProxyException;
}
