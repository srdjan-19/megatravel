package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.converter.AccommodationCategoryConverter;
import com.megatravel.dto.response.ResponseAccommodationCategory;
import com.megatravel.service.AccommodationCategoryService;

@Controller
@RequestMapping("/accommodation-categories")
public class AccommodationCategoryController {
	
	@Autowired
	private AccommodationCategoryService acService;
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationCategory>> findAll() {
		return ResponseEntity.ok(AccommodationCategoryConverter.fromEntityList(acService.findAll(), (category -> AccommodationCategoryConverter.toResponseFromEntity(category))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationCategory> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationCategoryConverter.toResponseFromEntity(acService.findById(id)));
	}
	
}