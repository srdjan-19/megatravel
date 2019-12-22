package com.megatravel.dto.response;

public class ResponseAccommodationCategory {
	
	private Long id;
	
	private String name;

	public ResponseAccommodationCategory() {
		
	}
	
	public ResponseAccommodationCategory(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
