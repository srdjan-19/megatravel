package com.megatravel.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.megatravel.dto.ResponseUser;

@Service
@FeignClient("main-backend")
public interface MainBackendService {
	
	@RequestMapping(value="/users/search", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUser> findUserByUsername(@RequestParam("username") String username);
	
}
