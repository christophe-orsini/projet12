package com.ocdev.financial.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ocdev.financial.beans.Aircraft;
import com.ocdev.financial.errors.ProxyException;

@FeignClient(name = "hangar-service")
public interface HangarProxy
{
	@GetMapping( value = "/aircrafts/id/{aircraftId}")
	public Aircraft getAircraftById(@PathVariable final long aircraftId) throws ProxyException;
}
