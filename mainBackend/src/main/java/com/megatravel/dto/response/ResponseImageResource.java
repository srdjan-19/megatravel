package com.megatravel.dto.response;

public class ResponseImageResource {
	
	private String path;

	public ResponseImageResource() {
		
	}
	
	public ResponseImageResource(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
