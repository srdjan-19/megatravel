package com.megatravel.dto;

import java.util.List;

public class LoginResponse {
	
	private String accessToken;
	
	private String username;
	
	private List<String> grantedAuthorities;
	
	public LoginResponse() {
		
	}

	public LoginResponse(String accessToken, String username, List<String> grantedAuthorities) {
		super();
		this.accessToken = accessToken;
		this.username = username;
		this.grantedAuthorities = grantedAuthorities;
	}

	public List<String> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(List<String> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
