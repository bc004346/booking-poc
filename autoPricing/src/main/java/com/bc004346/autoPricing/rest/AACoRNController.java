package com.bc004346.manualPricing.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AACoRNController {
    
	@RequestMapping(method=RequestMethod.POST, value="/pricing")
	public ResponseEntity<String> addFareCodes(@RequestBody String pnrBody)
	{
		try {
			System.out.println("Starting com.bc004346.manualPricing.rest.addFareCodes()");
			pnrBody = pnrBody + System.lineSeparator() + "Auto Pricing through SABRE";
			return new ResponseEntity<String>(pnrBody + System.lineSeparator() + "AACoRN  booking endorsements", HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}
}
