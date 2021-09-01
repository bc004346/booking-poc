package com.bc004346.orchestrator.data;

import com.bc004346.orchestrator.externalservices.PricingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataImpl implements Data {

    @Autowired
    private PricingService pricingService;

    @Override
    public String getPricing(String pnrBody) {
		System.out.println("Starting getPricing()");
        return pricingService.addPricing(pnrBody);
    }
    
}
