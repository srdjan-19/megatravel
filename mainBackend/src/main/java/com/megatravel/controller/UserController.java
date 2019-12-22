package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.EndUserConverter;
import com.megatravel.converter.ReservationConverter;
import com.megatravel.converter.UserConverter;
import com.megatravel.dto.response.ResponseEndUser;
import com.megatravel.dto.response.ResponseReservation;
import com.megatravel.dto.response.ResponseUser;
import com.megatravel.dto.soap.CreateEndUserRequest;
import com.megatravel.dto.soap.UserResponse;
import com.megatravel.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserResponse> save(@RequestBody CreateEndUserRequest request) throws Exception { 
		return ResponseEntity.ok(userService.completeEndUser(request));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/find/endusers", method = RequestMethod.GET)
	public ResponseEntity<List<ResponseEndUser>> test() {
		return ResponseEntity.ok(EndUserConverter.fromEntityList(userService.findEndUsers(), enduser -> EndUserConverter.toResponseFromEntity(enduser)));
	}
	
	@RequestMapping(value = "/find/username={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUser> findUser(@PathVariable("username") String username) {
		return ResponseEntity.ok(UserConverter.toResponseFromEntity(userService.findUser(username)));
	}
	
	@RequestMapping(value = "/find/enduser/username={username}", method = RequestMethod.GET)
	public ResponseEndUser findEndUser(@PathVariable("username") String username) {
		if (userService.findEndUser(username) != null) 
			return EndUserConverter.toResponseFromEntity(userService.findEndUser(username));
		else 
			return null;
	}
	
	@RequestMapping(value = "/find/enduser/email={email}", method = RequestMethod.GET)
	public ResponseEndUser findEndUserByEmail(@PathVariable("email") String email) {
		if (userService.findEndUserByEmail(email) != null) 
			return EndUserConverter.toResponseFromEntity(userService.findEndUserByEmail(email));
		else 
			return null;
	}
	
	//TODO sync with agent db!
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<List<ResponseEndUser>> deleteUser(@PathVariable("username") String username) {
		return ResponseEntity.ok(EndUserConverter.fromEntityList(userService.delete(username), enduser -> EndUserConverter.toResponseFromEntity(enduser)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/block/{username}", method = RequestMethod.PUT)
	public ResponseEntity<List<ResponseEndUser>> blockUser(@PathVariable String username) {
		return ResponseEntity.ok(EndUserConverter.fromEntityList(userService.block(username), enduser -> EndUserConverter.toResponseFromEntity(enduser)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/activate/{username}", method = RequestMethod.PUT)
	public ResponseEntity<List<ResponseEndUser>> activateUser(@PathVariable String username) {
		return ResponseEntity.ok(EndUserConverter.fromEntityList(userService.activate(username), enduser -> EndUserConverter.toResponseFromEntity(enduser)));
	}
	
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public ResponseEntity<List<ResponseReservation>> findMyReservations() {
		return ResponseEntity.ok(ReservationConverter.fromEntityList(userService.findMyReservations(),reservation -> ReservationConverter.toResponseFromEntity(reservation)));
	}
	
}
