package com.megatravel.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<ResponseAccommodation>> findAll() {
//		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findAll(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
//	}
	
	@RequestMapping(value="/page",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> findAll(@RequestParam("page") int num) {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findAll(num), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value="/owned", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> owned() {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList( accommodationService.findOwned(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ResponseAccommodation> create(@RequestBody CreateAccommodationRequest request, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(AccommodationConverter.toResponseFromEntity(accommodationService.create(request, user)));
	}
	
	//TODO Am i owner?
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ResponseAccommodation> update(@RequestBody UpdateAccommodationRequest request) {
		return ResponseEntity.ok(AccommodationConverter.toResponseFromEntity(accommodationService.update(request)));
	}
	
	//TODO Am i owner?
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<CudAccommodationResponse> delete(@PathVariable("id") long id){
		return ResponseEntity.ok(accommodationService.delete(id));		
	}
	
}
