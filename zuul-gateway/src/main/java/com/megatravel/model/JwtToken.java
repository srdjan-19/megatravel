package com.megatravel.model;

public class JwtToken {

	private long id;
	private String token;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
