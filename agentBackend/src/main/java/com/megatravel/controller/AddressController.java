package com.megatravel.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.model.Address;
import com.megatravel.service.AddressService;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/find/zip={zip}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Address findAddress(@PathVariable("zip") int zip) {
		return addressService.findByZip(zip);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/find/city={city}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Address findAddressByCity(@PathVariable("city") String city) {
		return addressService.findByCity(city);
	}

}
