package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.megatravel.dto.CreateAgentRequest;
import com.megatravel.dto.ResponseAgent;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.validator.UserValidator;

@Service
public class RegistrationService {
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AgentBackendService agentBackend;
	
	public ResponseAgent register(String authorizationHeader, CreateAgentRequest request, BindingResult bindingResults) {
		
		System.out.println("HEAADER: " + authorizationHeader);

		userValidator.validate(request, bindingResults);
		
		if (bindingResults.hasErrors()) {
			for (Object object : bindingResults.getAllErrors()) {
			    if(object instanceof FieldError) {
			        FieldError fieldError = (FieldError) object;
			        
			        throw new ExceptionResponse(fieldError.getCode().toString(), HttpStatus.BAD_REQUEST);
				}

			    if(object instanceof ObjectError) {
			        ObjectError objectError = (ObjectError) object;

			        throw new ExceptionResponse (objectError.getCode().toString(), HttpStatus.BAD_REQUEST);
			    }
			}
		}
		
		request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		return agentBackend.complete(authorizationHeader, request);
	}
	
}
