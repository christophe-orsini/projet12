package com.ocdev.gatewayservice.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class MainController
{
	@GetMapping("/ping")
    public Mono<String> ping()
	{
		SecurityContext context = SecurityContextHolder.getContext();
	    Authentication authentication = context.getAuthentication();
	    if (authentication != null) return Mono.just("Scopes: " + authentication.getAuthorities());
	    return Mono.just("Scopes");
    }

	@GetMapping("/")
    public Mono<String> index(WebSession session)
	{
		return Mono.just("Session Id : " + session.getId());
	}
}
