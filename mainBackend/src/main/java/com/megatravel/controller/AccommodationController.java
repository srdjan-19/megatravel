package com.megatravel.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AccommodationConverter;
import com.megatravel.dto.response.ResponseAccommodation;
import com.megatravel.model.Accommodation;
import com.megatravel.service.AccommodationService;


@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
	private static final Logger logger = LoggerFactory.getLogger(AccommodationController.class);
	
	@Autowired
	private AccommodationService accommodationService;
	
//	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
//	public ResponseEntity<List<ResponseAccommodation>> findAll() {
//		return ResponseEntity.ok(AccommodationConverter.fromEntityList(accommodationService.findAll(), acc -> AccommodationConverter.toResponseFromEntity((acc))));
//	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> findAll(@RequestParam("page") int num) {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList(accommodationService.findAll(num), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAccommodation>> search(@RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("category") String category) {
		return ResponseEntity.ok(AccommodationConverter.fromEntityList(accommodationService.search(name, type, category), acc -> AccommodationConverter.toResponseFromEntity((acc))));
	}
	
	@RequestMapping(path ="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<ResponseAccommodation> findById(@PathVariable Long id) {
		return ResponseEntity.ok(AccommodationConverter.toResponseFromEntity(accommodationService.findById(id)));
	}
		
}
