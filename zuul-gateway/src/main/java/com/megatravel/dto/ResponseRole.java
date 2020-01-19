package com.megatravel.dto;

import com.megatravel.model.Roles;

public class ResponseRole {

	protected Roles name;
	
	public ResponseRole() {
		
	}

    public ResponseRole(Roles name) {
		super();
		this.name = name;
	}

	public Roles getName() {
		return name;
	}

	public void setName(Roles name) {
		this.name = name;
	}
    
}
