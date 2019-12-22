package com.megatravel.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AddressConverter;
import com.megatravel.dto.response.ResponseAddress;
import com.megatravel.model.Address;
import com.megatravel.service.AddressService;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ResponseAddress>> get() {
		return ResponseEntity.ok(AddressConverter.fromEntityList(addressService.findAll(), address -> AddressConverter.toResponseFromEntity(address)));
	}
	
	@RequestMapping(value = "/find/zip={zip}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Address findAddress(@PathVariable("zip") int zip) {
		return addressService.findByZip(zip);
	}
	
	@RequestMapping(value = "/find/city={city}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Address findAddressByCity(@PathVariable("city") String city) {
		return addressService.findByCity(city);
	}

}
