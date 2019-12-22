package com.megatravel.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.megatravel.dto.request.CreateEndUserRequest;
import com.megatravel.dto.response.ResponseEndUser;
import com.megatravel.model.EndUser;
import com.megatravel.model.UserStatus;

@Service
public class RegistrationService {
	
	@Autowired
	private MainBackendService mainBackend;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public CreateEndUserRequest complete(CreateEndUserRequest user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
								
		return user;
	}
	
}
