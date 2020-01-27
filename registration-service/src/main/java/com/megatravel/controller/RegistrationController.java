package com.megatravel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.dto.request.CreateEndUserRequest;
import com.megatravel.service.MainBackendService;
import com.megatravel.service.RegistrationService;
import com.megatravel.validator.UserValidator;

@RestController
@RequestMapping(value = "/")
public class RegistrationController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private MainBackendService microService;
	
	@Autowired
	private UserValidator userValidator;
	
	
	public RegistrationController(MainBackendService microService, RegistrationService registrationService, UserValidator userValidator) {
		this.microService = microService;
		this.registrationService = registrationService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestBody CreateEndUserRequest user, BindingResult bindingResults) {
		logger.debug("Attempting to register user...");
		logger.debug("Validating user registration request...");
		userValidator.validate(user, bindingResults);
		
		if (bindingResults.hasErrors()) {
			logger.error("User registration request has one or more errors.");

			for (Object object : bindingResults.getAllErrors()) {
			    if(object instanceof FieldError) {
			        FieldError fieldError = (FieldError) object;
					logger.error(fieldError.getCode().toString());

			        return ResponseEntity.badRequest().body(fieldError.getCode().toString());
				}

			    if(object instanceof ObjectError) {
			        ObjectError objectError = (ObjectError) object;
					logger.error(objectError.getCode().toString());

			        return ResponseEntity.badRequest().body(objectError.getCode().toString());
			    }
			}
		}
		
		logger.info("Validation has completed successfully.");

		registrationService.complete(user);
			
		logger.info("Connecting to microservice 'main-backend'.");

		return microService.saveEndUser(user);
		
	}

}
