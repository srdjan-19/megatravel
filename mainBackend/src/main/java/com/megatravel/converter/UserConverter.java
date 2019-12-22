package com.megatravel.converter;

import com.megatravel.dto.response.ResponseUser;
import com.megatravel.model.User;

public class UserConverter extends AbstractConverter {

	public static ResponseUser toResponseFromEntity(User user) {
		return new ResponseUser(user.getUsername(), user.getPassword(), RoleConverter.fromEntityList(user.getRoles(), role -> RoleConverter.toResponseFromEntity(role)));
	}

}
