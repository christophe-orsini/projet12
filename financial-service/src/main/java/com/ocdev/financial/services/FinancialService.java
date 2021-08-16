package com.ocdev.financial.services;

import java.util.Collection;

import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;

public interface FinancialService
{
	/**
	 * Cette méthode retourne la liste des encours pour un membre.
	 * 
	 * @param memberId : Id du membre
	 * @param paid : True si le vol est payé sinon false
	 * @return La liste (peut être vide)
	 * @See {@link com.ocdev.financial.entities.Flight}
	 */
	public Collection<Flight> getAllFlights(String memberId, boolean paid);
	/**
	 * Cette méthode retourne un vol.
	 * 
	 * @param id : Id du vol
	 * @return Le vol
	 * * @throws EntityNotFoundException levée si le vol n'existe pas
	 * @See {@link com.ocdev.financial.entities.Flight}
	 */
	public Flight getFlight(long id) throws EntityNotFoundException;
	/**
	 * Cette méthode retourne la dernière cotisation d'un membre.
	 * 
	 * @param memberId : Id du membre
	 * @return La dernière cotisation
	 * @throws EntityNotFoundException levée si le membre n'existe pas ou s'il n'a jamais payé de cotisation
	 * @See {@link com.ocdev.financial.entities.Subscription}
	 */
	public Subscription getLastSubscription(String memberId) throws EntityNotFoundException;
	/**
	 * Cette méthode enregistre le paiement d'une cotisation pour un membre.
	 * <p>Si le champs amount est négatif, le montant forfaitaire de la cotisation est utilisé.</p>
	 * 
	 * @param subscriptionDto : Le DTO de cotisation
	 * @return La nouvelle cotisation
	 * @throws EntityNotFoundException levée si le membre n'existe pas
	 * @throws AlreadyExistsException levée si le membre est déja à jour de cotisation
	 * @See {@link com.ocdev.financial.entities.Subscription}
	 * @See {@link com.ocdev.financial.entities.SubscriptionDto}
	 */
	public Subscription recordSubscription(SubscriptionDto subscriptionDto) throws EntityNotFoundException, AlreadyExistsException;
	/**
	 * Vette méthode enregistre un vol.
	 * 
	 * @param flightDto : Le DTO d'enregistrement du vol
	 * @return Le nouveau vol
	 * @throws EntityNotFoundException levée si le membre n'existe pas
	 * @See {@link com.ocdev.financial.entities.Flight}
	 * @See {@link com.ocdev.financial.entities.FlightRecordDto}
	 */
	public Flight recordFlight(FlightRecordDto flightDto) throws EntityNotFoundException;
}
