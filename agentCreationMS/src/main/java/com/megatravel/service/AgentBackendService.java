package com.megatravel.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.dto.CreateAgentRequest;
import com.megatravel.dto.ResponseAddress;
import com.megatravel.dto.ResponseAgent;

@Service
@FeignClient("agent-backend")
public interface AgentBackendService {
	
	@RequestMapping(value="/agents/find/username={username}", method=RequestMethod.GET)
	public ResponseAgent findAgentByUsername(@PathVariable("username") String username);
	
	@RequestMapping(value="/agents/find/brn={brn}", method=RequestMethod.GET)
	public ResponseAgent findAgentByBrn(@PathVariable("brn") String brn);
	
	@RequestMapping(value="/agents", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ResponseAgent> complete(@RequestBody CreateAgentRequest request);
	
	@RequestMapping(value="/addresses/find/city={city}", method=RequestMethod.GET)
	public ResponseAddress findAddress(@PathVariable("city") String city);

}
