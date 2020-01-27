package com.megatravel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.megatravel.dto.request.CreateEndUserRequest;

@Service
public class RegistrationService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MainBackendService mainBackend;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public CreateEndUserRequest complete(CreateEndUserRequest user) {
		logger.debug("Completing user registration request...");
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
								
		return user;
	}
	
}
