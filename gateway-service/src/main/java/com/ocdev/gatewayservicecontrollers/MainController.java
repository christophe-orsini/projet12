package com.ocdev.gatewayservicecontrollers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MainController
{
	@GetMapping("/ping")
    public Mono<String> ping()
	{
		SecurityContext context = SecurityContextHolder.getContext();
	    Authentication authentication = context.getAuthentication();
	    return Mono.just("Scopes: " + authentication.getAuthorities());
    }
}