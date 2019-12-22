package com.megatravel.dto;

public class ResponseAccommodationType {

	private String name;
	
	public ResponseAccommodationType() {
		
	}

	public ResponseAccommodationType(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
