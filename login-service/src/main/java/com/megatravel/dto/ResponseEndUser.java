package com.megatravel.dto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.megatravel.model.UserStatus;

public class ResponseEndUser {
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	    
	private UserStatus status;
	
	private List<ResponseRole> roles;
	
	public ResponseEndUser() {
		
	}

	public ResponseEndUser(String username, String password, String email, String firstName, String lastName, UserStatus status) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public List<ResponseRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ResponseRole> roles) {
		this.roles = roles;
	}
    

}
