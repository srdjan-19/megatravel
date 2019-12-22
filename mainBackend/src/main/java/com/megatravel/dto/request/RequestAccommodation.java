package com.megatravel.dto.request;

public class RequestAccommodation {

	private String name;
	
	public RequestAccommodation() {
		
	}

	public RequestAccommodation(String name) {
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
