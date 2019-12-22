package com.megatravel.converter;

import com.megatravel.dto.response.ResponseCancellation;
import com.megatravel.model.Cancellation;

public class CanncellationConverter extends AbstractConverter {

	public static ResponseCancellation toResponseFromEntity(Cancellation cancellation) { 
		return new ResponseCancellation(cancellation.isAvailable(), cancellation.getPeriod());
	}
	
}
