package com.megatravel.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AccommodationConverter;
import com.megatravel.dto.response.ResponseAccommodation;
import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CudAccommodationResponse;
import com.megatravel.dto.soap.UpdateAccommodationRequest;
import com.megatravel.service.AccommodationService;


@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
		
	@Autowired
	private AccommodationService accommodationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ResponseAccommodation>> findAll() {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findAll(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value="/owned", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> owned() {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findOwned(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> create(@RequestBody CreateAccommodationRequest request) {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList(accommodationService.create(request), accommodation -> AccommodationConverter.toResponseFromEntity(accommodation)));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<CudAccommodationResponse> update(@RequestBody UpdateAccommodationRequest request) {
		return ResponseEntity.ok(accommodationService.update(request));
	}
	
	//TODO Am i owner?
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<CudAccommodationResponse> delete(@PathVariable("name") String name){
		return ResponseEntity.ok(accommodationService.delete(name));		
	}
	
}
