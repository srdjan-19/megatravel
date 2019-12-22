package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.dto.ResponseAgent;
import com.megatravel.dto.ResponseEndUser;
import com.megatravel.dto.ResponseUser;

@FeignClient(name="main-backend")
@Service
public interface MainBackendService {   

	@RequestMapping(value="/users/find/enduser/username={username}", method=RequestMethod.GET)
	public ResponseEndUser findEndUserByUsername(@PathVariable("username") String username);

	@RequestMapping(value="/users/find/agent/{username}", method=RequestMethod.GET)
	public ResponseAgent findAgentByUsername(@PathVariable("username") String username);
	
	@RequestMapping(value="/users/find/username={username}", method=RequestMethod.GET)
	public ResponseUser findUserByUsername(@PathVariable("username") String username);

}


