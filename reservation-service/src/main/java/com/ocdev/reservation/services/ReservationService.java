package com.ocdev.reservation.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import com.ocdev.reservation.beans.Aircraft;
import com.ocdev.reservation.dto.BookingCloseDto;
import com.ocdev.reservation.entities.Booking;
import com.ocdev.reservation.dto.BookingCreateDto;
import com.ocdev.reservation.errors.AlreadyExistsException;
import com.ocdev.reservation.errors.EntityNotFoundException;
import com.ocdev.reservation.errors.ProxyException;

public interface ReservationService
{
	/**
	 * Cette vérifie si un aéronef est disponible à une heure donnée et pour une durée determinée.
	 * <p>Un aéronef est disponible s'il n'est pas déjà réservé pour la période 
	 * et s'il n'est pas techniquement indisponible</p>
	 * 
	 * @param aircraftId : Id de l'aéronef
	 * @param startTime : Date et heure du départ prévue
	 * @param duration: Date et heure du départ prévue
	 * @return : true si l'aéronef est disponible, false sinon
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas 
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 */
	boolean isAircaftAvailable(long aircraftId, LocalDateTime startTime, double duration) throws EntityNotFoundException, ProxyException;
	/**
	 * Cette méthode retourne la liste des aéronefs ({@link Aircraft}) disponible à une heure donnée et pour une durée déterminée.
	 * <p>Un aéronef est disponible s'il n'est pas déjà réservé pour la période 
	 * et s'il n'est pas techniquement indisponible</p>
	 * 
	 * @param startTime : Date et heure du départ prévue
	 * @param duration : Date et heure du départ prévue
	 * @return : La liste des aéronefs disponibles
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 * @see Aircraft
	 */
	public Collection<Aircraft> availableAircrafts(LocalDateTime startTime, double duration) throws ProxyException;
	/**
	 * Cette méthode crée une nouvelle réservation.
	 * 
	 * @@Override
	param bookingCreateDto : DTO ({@link BookingCreateDto}) de création de la réservation 
	 * @return : La réservation ({@link Booking}) crée
	 * @throws EntityNotFoundException levée si le membre ou l'aéronef n'existe pas
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 * @throws AlreadyExistsException levée si l'aéronef est déja réservé pour la période
	 * @see BookingCreateDto
	 * @see Booking
	 */
	public Booking createBooking(BookingCreateDto bookingCreateDto) throws EntityNotFoundException, ProxyException, AlreadyExistsException;
	/**
	 * Cette méthode retourne la liste des réservations en cours pour un membre.
	 * 
	 * @param memberId : Id du membre
	 * @param closed : True pour la liste des réservations termlinées ou False pour la liste des réservations en cours
	 * @return : La liste des réservations
	 * @throws EntityNotFoundException levée si le membre n'existe pas
	 * @see Booking
	 */
	public Collection<Booking> getAllBookings(String memberId, boolean closed) throws EntityNotFoundException;
	/***
	 * Cette méthode supprime une réservation existante et non cloturée.
	 * 
	 * @param reservationId : Id de la réservation
	 * @throws EntityNotFoundException levée si la réservation n'existe pas
	 */
	public void deleteBooking(long reservationId) throws EntityNotFoundException;
	/***
	 * Cette méthode clôture et enregistre un vol.
	 * 
	 * @param reservationId : Id de la réservation à clôturer
	 * @param bookingCloseDto : Dto de clôture du vol
	 * @return : La réservation clôturée
	 * @throws EntityNotFoundException levée si la réservation n'existe pas
	 * @throws ProxyException levée en cas d'erreur de requête au service hangar
	 */
	public Booking closeBooking(long reservationId, BookingCloseDto bookingCloseDto) throws EntityNotFoundException, ProxyException;
	/**
	 * Cette méthode retourne la liste des réservations en cours pour un aéronef et pour une date.
	 * 
	 * @param memberId : Id de l'aéronef
	 * @param date : La journée
	 * @return : La liste des réservations
	 * @throws EntityNotFoundException levée si l'aéronef n'existe pas
	 * @see Booking
	 */
	public Collection<Booking> getAllBookings(long aircraftId, LocalDate date) throws EntityNotFoundException;
	/***
	 * Cette méthode retourne une réservation.
	 * 
	 * @param reservationId : Id de la réservation
	 * @return : La réservation
	 * @throws EntityNotFoundException levée si la réservation n'existe pas
	 */
	public Booking getBooking(long reservationId) throws EntityNotFoundException;
	
}
