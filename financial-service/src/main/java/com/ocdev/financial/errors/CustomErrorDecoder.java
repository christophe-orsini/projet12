package com.ocdev.financial.errors;

import org.apache.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoker, Response response)
    {
    	if (response.status() != HttpStatus.SC_OK || response.status() != HttpStatus.SC_CREATED)
    	{
    		return new ProxyException("Erreur générale d'accès au service partenaire");
    	}
    	
        return defaultErrorDecoder.decode(invoker, response);
    }

}
