package com.ocdev.financial.services;

import java.util.Collection;

import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Membership;
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
	 * Cette méthode retourne l'adhésion d'un membre.
	 * 
	 * @param memberId : Id du membre
	 * @return
	 * @throws EntityNotFoundException
	 */
	public Membership getMembership(long memberId) throws EntityNotFoundException;
}
