package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.dto.ResponseAgent;

@FeignClient(name="agent-backend")
@Service
public interface AgentBackendService {
	
	@RequestMapping(value="/agents/username={username}", method=RequestMethod.GET)
	public ResponseAgent findAgentByUsername(@PathVariable("username") String username);

}
