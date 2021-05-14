package com.ocdev.reservation.errors;

/**
 * Classe principale des exceptions controlées de l'application.
 * @author C.Orsini
 *
 */
public class ServicesException extends Exception
{

	public ServicesException()
	{
		super();
	}

	public ServicesException(String message)
	{
		super(message);
	}
}
