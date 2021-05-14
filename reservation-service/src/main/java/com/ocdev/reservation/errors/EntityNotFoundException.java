package com.ocdev.reservation.errors;

/**
 * Classe exception pour une entité non trouvée et obligatoire.
 * Levé lorsque une recherche n'aboutit pas et qu'elle devrait.
 * Http Status Code : {@link org.springframework.http.HttpStatus.NOT_FOUND;}
 * @author C.Orsini
 */
public class EntityNotFoundException extends ServicesException
{
	public EntityNotFoundException(String message)
	{
		super(message);
	}
}
