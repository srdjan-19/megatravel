package com.megatravel.dto;

import java.util.List;

public class ResponseUser {
	
	private String username;
	
	private String password;
	
	private List<ResponseRole> roles;
	
	public ResponseUser() {
		
	}

	public ResponseUser(String username, String password, List<ResponseRole> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
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

	public List<ResponseRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ResponseRole> roles) {
		this.roles = roles;
	}

}
