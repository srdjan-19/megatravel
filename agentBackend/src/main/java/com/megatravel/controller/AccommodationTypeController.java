package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AccommodationTypeConverter;
import com.megatravel.dto.response.ResponseAccommodationType;
import com.megatravel.service.AccommodationTypeService;

@RestController
@RequestMapping("/accommodation-types")
public class AccommodationTypeController {

	@Autowired
	private AccommodationTypeService atService;
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> findAll() {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(atService.findAll(), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationType> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationTypeConverter.toResponseFromEntity(atService.findById(id)));
	}
	
}