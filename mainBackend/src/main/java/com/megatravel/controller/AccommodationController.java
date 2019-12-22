package com.megatravel.controller;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AccommodationConverter;
import com.megatravel.dto.response.ResponseAccommodation;
import com.megatravel.model.User;
import com.megatravel.service.AccommodationService;


@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
	private static final Logger logger = LoggerFactory.getLogger(AccommodationController.class);
	
	@Autowired
	private AccommodationService accommodationService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> findAll() {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findAll(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
		
}
