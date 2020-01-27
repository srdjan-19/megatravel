package com.megatravel.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.dto.CreateAgentRequest;
import com.megatravel.dto.ResponseAgent;
import com.megatravel.service.RegistrationService;

@RestController
@RequestMapping(value = "/")
public class RegistrationController {
	
	private RegistrationService registrationService;
	
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	//TODO Cannot register without address
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAgent> register(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody CreateAgentRequest request, BindingResult bindingResult) {
		return ResponseEntity.ok(registrationService.register(authorizationHeader, request, bindingResult));
	}

}
