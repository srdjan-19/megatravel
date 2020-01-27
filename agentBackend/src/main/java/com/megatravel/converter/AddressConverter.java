package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAddress;
import com.megatravel.dto.soap.CreateAddressRequest;
import com.megatravel.model.Address;

public class AddressConverter extends AbstractConverter {
		
	public static ResponseAddress toResponseFromEntity(Address address) {
		return new ResponseAddress(address.getStreet(), address.getCity(), address.getCountry(), address.getZip(), address.getLatitude(), address.getLongitude());
	}
	
	public static Address toEntityFromRequest(CreateAddressRequest request) {
		return new Address(request.getCountry(), request.getCity(), request.getZip(), request.getStreet(), request.getLongitude(), request.getLatitude());
	}
	
}
