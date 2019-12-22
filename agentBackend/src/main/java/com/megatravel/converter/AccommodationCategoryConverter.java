package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAccommodationCategory;
import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.model.AccommodationCategory;

public class AccommodationCategoryConverter extends AbstractConverter {

	public static ResponseAccommodationCategory toResponseFromEntity(AccommodationCategory category) {
		return new ResponseAccommodationCategory(category.getName(), category.getId());
	}
	
	public static AccommodationCategory toEntityFromRequest(CreateAccommodationCategoryRequest request) {
		AccommodationCategory category = new AccommodationCategory();
		category.setName(request.getName());
		return category;
	}
}
