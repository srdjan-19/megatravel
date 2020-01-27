package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.megatravel.converter.AccommodationCategoryConverter;
import com.megatravel.dto.response.ResponseAccommodationCategory;
import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.dto.soap.UpdateAccommodationCategoryRequest;
import com.megatravel.service.AccommodationCategoryService;

@Controller
@RequestMapping("/accommodation-categories")
public class AccommodationCategoryController {
	
	@Autowired
	private AccommodationCategoryService accommodationCategoryService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationCategory> create(@RequestBody CreateAccommodationCategoryRequest request) {
		return ResponseEntity.ok(AccommodationCategoryConverter.toResponseFromEntity(accommodationCategoryService.create(request)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationCategory>> findAll() {
		return ResponseEntity.ok(AccommodationCategoryConverter.fromEntityList(accommodationCategoryService.findAll(), (category -> AccommodationCategoryConverter.toResponseFromEntity(category))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationCategory> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationCategoryConverter.toResponseFromEntity(accommodationCategoryService.findById(id)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationCategory> update(@RequestBody UpdateAccommodationCategoryRequest request) {
		return ResponseEntity.ok(AccommodationCategoryConverter.toResponseFromEntity(accommodationCategoryService.modify(request)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> delete(@PathVariable Long id) {
		return ResponseEntity.ok(accommodationCategoryService.delete(id));
	}
	
}