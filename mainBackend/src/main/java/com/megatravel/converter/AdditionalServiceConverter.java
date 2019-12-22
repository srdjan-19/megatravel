package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAdditionalService;
import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.model.AdditionalService;

public class AdditionalServiceConverter extends AbstractConverter {
	
	public static ResponseAdditionalService toResponseFromEntity(AdditionalService additionalService) {
		return new ResponseAdditionalService(additionalService.getId(), additionalService.getName());
	}
	
	public static AdditionalService toEntityFromRequest(CreateAdditionalServiceRequest request) {
		AdditionalService as = new AdditionalService();
		as.setName(request.getName());
		return as;
	}
}
