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
	private AccommodationTypeService atService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> create(@RequestBody CreateAccommodationTypeRequest request) {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(atService.create(request), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> findAll() {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(atService.findAll(), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> update(@RequestBody UpdateAccommodationTypeRequest request) {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(atService.modify(request), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAccommodationType>> delete(@PathVariable("name") String name) {
		return ResponseEntity.ok(AccommodationTypeConverter.fromEntityList(atService.delete(name), (type -> AccommodationTypeConverter.toResponseFromEntity(type))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAccommodationType> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationTypeConverter.toResponseFromEntity(atService.findById(id)));
	}
	
}