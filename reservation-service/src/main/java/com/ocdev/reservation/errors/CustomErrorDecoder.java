package com.ocdev.reservation.errors;

import org.apache.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoker, Response response)
    {
    	switch (response.status())
    	{
    		case HttpStatus.SC_NOT_FOUND:
    			return new EntityNotFoundException("Non trouvé");
    		case 460:
    			return new AlreadyExistsException("Déjà existant");
    	}
    	
        return defaultErrorDecoder.decode(invoker, response);
    }

}
