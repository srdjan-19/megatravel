package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAccommodationType;
import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.model.AccommodationType;

public class AccommodationTypeConverter extends AbstractConverter {

	public static ResponseAccommodationType toResponseFromEntity(AccommodationType type) {
		return new ResponseAccommodationType(type.getName(), type.getId());
	}
	
	public static AccommodationType toEntityFromRequest(CreateAccommodationTypeRequest request) {
		AccommodationType response = new AccommodationType();
		response.setName(request.getName());
		return response;
	}
	
}
