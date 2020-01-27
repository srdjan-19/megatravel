package com.megatravel.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AgentConverter;
import com.megatravel.dto.response.ResponseAgent;
import com.megatravel.service.UserService;


@RestController
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAgent>> findAgents() {
		return ResponseEntity.ok(AgentConverter.fromEntityList(userService.findAgents(), agent -> AgentConverter.toResponseFromEntity(agent)));
	}
 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/username={username}", method = RequestMethod.GET)
	public ResponseEntity<ResponseAgent> findByUsername(@PathVariable("username") String username) {
		if (userService.findAgent(username) != null) 
			return ResponseEntity.ok(AgentConverter.toResponseFromEntity(userService.findAgent(username)));
		else 
			return null;	
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/brn={brn}", method = RequestMethod.GET)
	public ResponseEntity<ResponseAgent> findByBrn(@PathVariable("brn") String username) {
		if (userService.findAgent(username) != null) 
			return ResponseEntity.ok(AgentConverter.toResponseFromEntity(userService.findAgent(username)));
		else 
			return null;	
	}
	
}
