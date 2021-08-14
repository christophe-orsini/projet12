package com.ocdev.airclub.errors;

/**
 * Exception levée si une entité existe déjà.
 * 
 * Levé lorsque une entité existe déjà et que l'on ne peut pas en créer une autre.
 * Http Status Code : {@link com.ocdev.airclub.errors.ServicesHttpStatus#SERVICES_DUPLICATE}
 * @author C.Orsini
 */
public class AlreadyExistsException extends ServicesException
{
	
	public AlreadyExistsException(String message)
	{
		super(message);
	}
}
