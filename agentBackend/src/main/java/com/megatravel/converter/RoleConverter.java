package com.megatravel.converter;

import com.megatravel.dto.response.ResponseRole;
import com.megatravel.model.Role;

public class RoleConverter extends AbstractConverter {

	public static ResponseRole toResponseFromEntity(Role role) {
		return new ResponseRole(role.getName());
	}

}
