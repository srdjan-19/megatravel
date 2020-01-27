package com.megatravel.converter;

import com.megatravel.dto.response.ResponsePriceInSeason;
import com.megatravel.model.PriceInSeason;

public class PriceInSeasonConverter extends AbstractConverter {
	
	public static ResponsePriceInSeason toResponseFromEntity(PriceInSeason pis) {
		return new ResponsePriceInSeason(pis.getMonth(), pis.getPrice(), pis.getCurrency());
	}

}
