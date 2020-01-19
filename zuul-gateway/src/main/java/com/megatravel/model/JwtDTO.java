package com.megatravel.model;

public class JwtDTO {
	
	private String accessToken;
	
	private String type;
	
	private String username;
	
	public JwtDTO() {
		
	}

	public JwtDTO(String accessToken, String type, String username) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
