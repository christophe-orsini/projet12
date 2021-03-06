package com.ocdev.reservation.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ocdev.reservation.beans.Subscription;
import com.ocdev.reservation.errors.ProxyException;

@FeignClient(name = "financial-service")
public interface FinancialProxy
{
	@GetMapping( value = "/subscriptions/{memberId}")
	public ResponseEntity<Subscription> getSubscription(@PathVariable final String memberId) throws ProxyException;
}
