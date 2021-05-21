package com.ocdev.financial.services;

import java.util.Collection;

import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.EntityNotFoundException;

public interface FinancialService
{
	/**
	 * Cette méthode retourne la liste des encours pour un membre.
	 * 
	 * @param memberId : Id du membre
	 * @return La liste (peut être vide)
	 * @See {@link com.ocdev.financial.entities.Flight}
	 */
	public Collection<Flight> getAllFlights(long memberId);
	/**
	 * Cette méthode retourne la dernière cotisation d'un membre.
	 * 
	 * @param memberId : Id du membre
	 * @return La dernière cotisation
	 * @throws EntityNotFoundException levée si le membre n'existe pas ou s'il n'a jamais payé de cotisation
	 */
	public Subscription getLastSubscription(long memberId) throws EntityNotFoundException;
}
