package com.ocdev.reservation.proxies;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ocdev.reservation.beans.Aircraft;

@FeignClient(name = "hangar-service")
public interface HangarProxy
{
	@GetMapping( value = "/aircrafts/{registration}")
	public Aircraft getAircraft(@PathVariable final String registration);
	
	@GetMapping( value = "/aircrafts")
	public Collection<Aircraft> getAircrafts();
}
