package com.bc004346.orchestrator.rest;

import com.bc004346.orchestrator.data.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorController {
    
	@Autowired
	private Data data;
	
	@RequestMapping(method=RequestMethod.POST, value="/reservation")
	public ResponseEntity<String> createReservation()
	{
		System.out.println("Starting createReservation()");
		try {
			String pnrBody = "Add Passengers and other common items shared between teams";
			pnrBody = pnrBody + data.getPricing(pnrBody);
			return new ResponseEntity<String>(pnrBody, HttpStatus.OK);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage() + System.lineSeparator() + e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(method=RequestMethod.GET, value="/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<>("ping", HttpStatus.OK);
	}
}
