package com.ocdev.airclub.errors;

/**
 * Classe exception pour un problème d'accès à un microservice.
 * Levé lorsque une requète à un microservice échoue.
 * Http Status Code : {@link org.springframework.http.HttpStatus.BAD_GATEWAY;}
 * @author C.Orsini
 */
public class ProxyException extends ServicesException
{
	public ProxyException(String message)
	{
		super(message);
	}
}
