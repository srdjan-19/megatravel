package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAddress;
import com.megatravel.model.Address;

public class AddressConverter extends AbstractConverter{
		
	public static ResponseAddress toResponseFromEntity(Address address) {
		return new ResponseAddress(address.getStreet(), address.getCity(), address.getCountry(), address.getZip(), address.getLatitude(), address.getLongitude());
	}
	
}
