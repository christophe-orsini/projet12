package com.ocdev.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static  org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
    		ReactiveClientRegistrationRepository clientRegistrationRepository)
	{
		http.authorizeExchange(exchanges -> exchanges
			.anyExchange().authenticated())
	        .oauth2Login(withDefaults());
		http.csrf().disable();
		
		http.logout(logout -> logout.logoutSuccessHandler(
	            new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
		http.oauth2ResourceServer().jwt();

        return http.build();
    }
}
