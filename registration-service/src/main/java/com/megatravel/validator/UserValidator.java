package com.megatravel.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.megatravel.dto.request.CreateEndUserRequest;
import com.megatravel.model.EndUser;
import com.megatravel.model.User;
import com.megatravel.service.MainBackendService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private MainBackendService microService;
	
	@Override
	public boolean supports(Class aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object entry, Errors errors) {
		
		CreateEndUserRequest user  = (CreateEndUserRequest) entry;
		
		ValidationUtils.rejectIfEmpty(errors, "username", "NotEmpty");
		if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
	        errors.rejectValue("username", "Username too short.");
	    }
	    if (microService.findEndUser(user.getUsername()) != null) {
	        errors.rejectValue("username", "Username already taken.");
	    }

	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
	        errors.rejectValue("password", "Password too short.");
	    }
	    
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
	    if (user.getEmail().contains("@") == false ) {
	        errors.rejectValue("email", "The email does not contain @");
	    }
	    if (user.getEmail().endsWith(".com") == false ) {
	        errors.rejectValue("email", "The mail does not contain .com at the end.");
	    }	
		
	}

}
