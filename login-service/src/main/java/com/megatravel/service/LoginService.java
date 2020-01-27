package com.megatravel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.megatravel.dto.LoginRequest;
import com.megatravel.dto.LoginResponse;
import com.megatravel.dto.ResponseRole;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.security.JwtTokenProvider;
import com.megatravel.security.UserDetailsImplementation;

@Service
public class LoginService implements LoginInterface {	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsImplementation userService;

    @Override
    public LoginResponse login(LoginRequest request) {
    	logger.debug("User " + request.getUsername() + " is trying to log in...");
    	 try {

             User user = userService.loadUserByUsername(request.getUsername());
             		
             if (user.getAuthorities().isEmpty()) {
            	 logger.error("User has unknown role!");
                 throw new ExceptionResponse("Unrecognized role.", HttpStatus.UNAUTHORIZED);
             }

             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, request.getPassword(), user.getAuthorities()));

             
             String token =  jwtTokenProvider.createToken(request.getUsername(), user.getAuthorities().stream()
            		 		.map((GrantedAuthority role)-> role.getAuthority()).filter(Objects::nonNull).collect(Collectors.toList()));
             
             logger.info("Setting user's roles...");
             List<String> grantedAuthorities = new ArrayList<String>(); 
             for (GrantedAuthority role : user.getAuthorities()) {
			 	 grantedAuthorities.add(role.getAuthority());
	   		 }
             
             logger.info("User " + user.getUsername() + " has been signed in successfully.");

             return new LoginResponse(token, user.getUsername(), grantedAuthorities);

         } catch (AuthenticationException e) {
        	 logger.error("Invalid user login request.", e);
             throw new ExceptionResponse("Invalid username/password combination", HttpStatus.UNAUTHORIZED);
             
         }
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String generateAuthToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String>roleList = jwtTokenProvider.getRoleList(token);
        String newToken =  jwtTokenProvider.createToken(username,roleList);
        return newToken;
    }

}
