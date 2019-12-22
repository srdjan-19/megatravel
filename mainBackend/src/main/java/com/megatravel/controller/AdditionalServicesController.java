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

import com.megatravel.converter.AdditionalServiceConverter;
import com.megatravel.dto.response.ResponseAdditionalService;
import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.dto.soap.UpdateAdditionalServiceRequest;
import com.megatravel.service.AdditionalServicesService;

@RestController
@RequestMapping("/additional-services")
public class AdditionalServicesController {
	
	@Autowired
	private AdditionalServicesService asService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAdditionalService>> create(@RequestBody CreateAdditionalServiceRequest request) {
		return ResponseEntity.ok(AdditionalServiceConverter.fromEntityList(asService.create(request), (aditionalService -> AdditionalServiceConverter.toResponseFromEntity(aditionalService))));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAdditionalService>> findAll() {
		return ResponseEntity.ok(AdditionalServiceConverter.fromEntityList(asService.findAll(), (aditionalService -> AdditionalServiceConverter.toResponseFromEntity(aditionalService))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAdditionalService>> update(@RequestBody UpdateAdditionalServiceRequest request) {
		return ResponseEntity.ok(AdditionalServiceConverter.fromEntityList(asService.modify(request), (aservice -> AdditionalServiceConverter.toResponseFromEntity(aservice))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAdditionalService>> delete(@PathVariable("name") String name) {
		return ResponseEntity.ok(AdditionalServiceConverter.fromEntityList(asService.delete(name), (aservice -> AdditionalServiceConverter.toResponseFromEntity(aservice))));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseAdditionalService> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AdditionalServiceConverter.toResponseFromEntity(asService.findById(id)));
	}
	
}