package com.megatravel.dto.response;

public class ResponseAccommodationType {

	private String name;
	
	private Long id;

	public ResponseAccommodationType() {
		
	}
	
	public ResponseAccommodationType(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
