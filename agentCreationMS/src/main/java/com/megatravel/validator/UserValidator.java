package com.megatravel.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.megatravel.dto.CreateAgentRequest;
import com.megatravel.dto.ResponseAgent;
import com.megatravel.service.AgentBackendService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private AgentBackendService agentService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return CreateAgentRequest.class.equals(aClass);
	}

	@Override
	public void validate(Object entry, Errors errors) {
		
		CreateAgentRequest request  = (CreateAgentRequest) entry;
		
		ResponseAgent agent = agentService.findAgentByUsername(request.getUsername());
		
		ValidationUtils.rejectIfEmpty(errors, "username", "username.required", "Enter username");
		if (request.getUsername().length() < 6 || request.getUsername().length() > 32) {
	        errors.rejectValue("username", "Username too short.");
	    }
	    if (agent != null) {
	        errors.rejectValue("username", "Username already taken.");
	    }

	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Enter password");
	    if (request.getPassword().length() < 8 || request.getPassword().length() > 32) {
	        errors.rejectValue("password", "Password too short.");
	    }
	    
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Enter email");
	    if (request.getEmail().contains("@") == false ) {
	        errors.rejectValue("email", "The email does not contain @");
	    }
	    if (request.getEmail().endsWith(".com") == false ) {
	        errors.rejectValue("email", "The mail does not contain .com at the end.");
	    }	
	    
		ValidationUtils.rejectIfEmpty(errors, "brn", "brn.required", "Enter brn");
		ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.required", "Enter first name");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.required", "Enter last name");
		ValidationUtils.rejectIfEmpty(errors, "city", "city.required", "Enter city");


		
	}

}
