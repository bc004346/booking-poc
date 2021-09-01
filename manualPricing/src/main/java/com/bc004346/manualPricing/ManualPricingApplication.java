package com.bc004346.manualPricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ManualPricingApplication {
    public static void main(String[] args) {
		SpringApplication.run(ManualPricingApplication.class, args); 
	}
}
