package com.megatravel.service;

import org.springframework.stereotype.Service;

import com.megatravel.dto.LoginRequest;
import com.megatravel.dto.LoginResponse;

@Service
public interface LoginInterface {
	 LoginResponse login(LoginRequest request);
	 
	 Boolean isValidToken(String token);

	 String generateAuthToken(String token);
	 
}
 