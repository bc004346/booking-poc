package com.bc004346.orchestrator.externalservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="pricingService", url="pricing.default.svc.cluster.local:8080")
public interface PricingService {
	@RequestMapping(method=RequestMethod.POST, value="/pricing")
	public String addPricing(@RequestBody String pnrBody); 
}
