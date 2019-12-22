package com.megatravel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.megatravel.dto.LoginRequest;
import com.megatravel.dto.LoginResponse;
import com.megatravel.dto.ResponseEndUser;
import com.megatravel.dto.ResponseRole;
import com.megatravel.dto.ResponseUser;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.security.JwtTokenProvider;

@Service
public class LoginService implements LoginInterface {	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private MainBackendService mainBackend;

    @Override
    public LoginResponse login(LoginRequest request) {
    	 try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

             ResponseUser user = mainBackend.findUserByUsername(request.getUsername()).getBody();
             		
             if (user.getRoles().isEmpty()) {
                 throw new ExceptionResponse("Unrecognized role.", HttpStatus.UNAUTHORIZED);
             }

             String token =  jwtTokenProvider.createToken(request.getUsername(), user.getRoles().stream()
            		 		.map((ResponseRole role)-> "ROLE_"+role.getName().name()).filter(Objects::nonNull).collect(Collectors.toList()));
             
             
            List<String> grantedAuthorities = new ArrayList<String>(); 
            for (ResponseRole role : user.getRoles()) {
				grantedAuthorities.add("ROLE_" + role.getName());
			}
             
             return new LoginResponse(token, user.getUsername(), grantedAuthorities);

         } catch (AuthenticationException e) {
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
