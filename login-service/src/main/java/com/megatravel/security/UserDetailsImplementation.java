package com.megatravel.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.megatravel.dto.ResponseRole;
import com.megatravel.dto.ResponseUser;
import com.megatravel.service.MainBackendService;

@Service
public class UserDetailsImplementation implements UserDetailsService {

	@Autowired
	private MainBackendService mainBackend;
		
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		ResponseUser client = mainBackend.findUserByUsername(username).getBody();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		   
	    for (ResponseRole role : client.getRoles()) {
	        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
	    }
		
		return new User(client.getUsername(), client.getPassword(), grantedAuthorities);
	}


	

}
