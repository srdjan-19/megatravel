package com.megatravel.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseAgent {

	private String username;
	
	private String password;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private int brn;
	    
	private List<ResponseRole> roles;
    
    public ResponseAgent() {
    	this.roles = new ArrayList<ResponseRole>();
    }
    
    public ResponseAgent(String username, String password, String email, String firstName, String lastName, int brn) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.brn = brn;
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


	public int getBrn() {
		return brn;
	}

	public void setBrn(int brn) {
		this.brn = brn;
	}

	public List<ResponseRole> getRoles() {
		return roles;
	}


	public void setRoles(List<ResponseRole> roles) {
		this.roles = roles;
	}    
}
