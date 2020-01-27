package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AccommodationTypeConverter;
import com.megatravel.dto.response.ResponseAccommodationType;
import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.dto.soap.UpdateAccommodationTypeRequest;
import com.megatravel.service.AccommodationTypeService;

@RestController
@RequestMapping("/accommodation-types")
public class AccommodationTypeController {

	@Autowired
	private AccommodationTypeService accommodationTypeService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationType> create(@RequestBody CreateAccommodationTypeRequest request) {
		return ResponseEntity.ok(AccommodationTypeConverter.toResponseFromEntity(accommodationTypeService.create(request)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> findAll() {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(accommodationTypeService.findAll(), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationType> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationTypeConverter.toResponseFromEntity(accommodationTypeService.findById(id)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationType> update(@RequestBody UpdateAccommodationTypeRequest request) {
		return ResponseEntity.ok(AccommodationTypeConverter.toResponseFromEntity(accommodationTypeService.modify(request)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> delete(@PathVariable Long id) {
		return ResponseEntity.ok(accommodationTypeService.delete(id));
	}

	
}