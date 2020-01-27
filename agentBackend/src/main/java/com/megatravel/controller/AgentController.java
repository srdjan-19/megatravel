package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AgentConverter;
import com.megatravel.converter.ReservationConverter;
import com.megatravel.converter.UserConverter;
import com.megatravel.dto.response.ResponseAgent;
import com.megatravel.dto.response.ResponseReservation;
import com.megatravel.dto.response.ResponseUser;
import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.service.UserService;


@RestController
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAgent> completeRegistration(@RequestBody CreateAgentRequest request) {
		return ResponseEntity.ok(AgentConverter.toResponseFromEntity(userService.complete(request)));
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ResponseUser> findByUsername(@RequestParam("username") String username) {
		if (userService.findAgent(username) != null) 
			return ResponseEntity.ok(UserConverter.toResponseFromEntity(userService.findUser(username)));
		else 
			return null;	
	}
	
	@RequestMapping(value="/find/brn={brn}", method = RequestMethod.GET)
	public ResponseEntity<ResponseAgent> findByBrn(@PathVariable("brn") String username) {
		if (userService.findAgent(username) != null) 
			return ResponseEntity.ok(AgentConverter.toResponseFromEntity(userService.findAgent(username)));
		else 
			return null;	
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value = "/reservations", method = RequestMethod.GET)
	public ResponseEntity<List<ResponseReservation>> findMyReservations(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(ReservationConverter.fromEntityList(userService.findMyReservations(user.getUsername()),reservation -> ReservationConverter.toResponseFromEntity(reservation)));
	}
			
}
