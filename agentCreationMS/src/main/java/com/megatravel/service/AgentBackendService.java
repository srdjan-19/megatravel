package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.megatravel.dto.CreateAgentRequest;
import com.megatravel.dto.ResponseAddress;
import com.megatravel.dto.ResponseAgent;

@Service
@FeignClient("agent-backend")
public interface AgentBackendService {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/agents", method=RequestMethod.GET)
	public ResponseAgent findAgentByUsername(@RequestParam("username") String username);
	
	@RequestMapping(value="/agents/find/brn={brn}", method=RequestMethod.GET)
	public ResponseAgent findAgentByBrn(@PathVariable("brn") String brn);
	
	@RequestMapping(value="/agents", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseAgent complete(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody CreateAgentRequest request);
	
	@RequestMapping(value="/addresses/find/city={city}", method=RequestMethod.GET)
	public ResponseAddress findAddress(@PathVariable("city") String city);

}
