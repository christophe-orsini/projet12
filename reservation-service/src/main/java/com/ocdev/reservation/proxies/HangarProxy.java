package com.ocdev.reservation.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ocdev.reservation.beans.Aircraft;

@FeignClient(name = "hangar-service", url = "localhost:8080/hangar/")
public interface HangarProxy
{
	@GetMapping( value = "/aircrafts/{registration}")
	public ResponseEntity<Aircraft> getAircraft(@PathVariable final String registration);		
}
