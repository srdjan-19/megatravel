package com.megatravel.dto.response;

public class ResponseAdditionalService {
	
	private Long id;

	private String name;

	public ResponseAdditionalService() {
		
	}

	public ResponseAdditionalService(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
