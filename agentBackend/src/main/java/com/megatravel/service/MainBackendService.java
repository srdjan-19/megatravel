package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.dto.response.ResponseEndUser;
import com.megatravel.dto.response.ResponseUser;

@Service
@FeignClient("main-backend")
public interface MainBackendService {
	
	@RequestMapping(value="/users/find/enduser/username={username}", method=RequestMethod.GET)
	public ResponseEndUser findEndUserByUsername(@PathVariable("username") String username);

	@RequestMapping(value="/users/find/username={username}", method=RequestMethod.GET)
	public ResponseUser findUserByUsername(@PathVariable("username") String username);
	
}
