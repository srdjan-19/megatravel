package com.megatravel.converter;

import com.megatravel.dto.response.ResponseEndUser;
import com.megatravel.dto.soap.CreateEndUserRequest;
import com.megatravel.model.EndUser;
import com.megatravel.model.UserStatus;

public class EndUserConverter extends AbstractConverter{
	
	public static ResponseEndUser toResponseFromEntity(EndUser enduser) {
		ResponseEndUser response = new ResponseEndUser(enduser.getUsername(), enduser.getPassword(), enduser.getEmail(), enduser.getFirstName(), enduser.getLastName(), enduser.getStatus());
 		response.setRoles(RoleConverter.fromEntityList(enduser.getRoles(), role -> RoleConverter.toResponseFromEntity(role)));
		//response.setReservations(ReservationConverter.fromEntityList(enduser.getReservations(), r -> ReservationConverter.toResponseFromEntity(r)));
 		return response;
	}
	
	public static EndUser toEntityFromRequest(CreateEndUserRequest request) {
		EndUser user = new EndUser();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPassword(request.getPassword());
		user.setStatus(UserStatus.ACTIVE);
		return user;
	}
	
}
