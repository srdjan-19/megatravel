package com.megatravel.converter;

import com.megatravel.dto.request.RequestRate;
import com.megatravel.dto.response.ResponseRate;
import com.megatravel.model.Rate;

public class RateConverter extends AbstractConverter {
	
	public static ResponseRate toResponseFromEntity(Rate r) {
		return new ResponseRate(r.getRate());
	}
	
	public static Rate toEntityFromRequest(RequestRate request) {
		Rate rate = new Rate();
		rate.setRate(request.getRate());
		return rate;
	}

}
