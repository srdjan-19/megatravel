package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.megatravel.dto.response.ResponseUser;

@Service
@FeignClient("main-backend")
public interface MainBackendService {
	
	@RequestMapping(value="/users/search", method=RequestMethod.GET)
	public ResponseUser findUserByUsername(@RequestParam("username") String username);
	
}
