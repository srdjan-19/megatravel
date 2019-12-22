package com.megatravel.converter;

import com.megatravel.dto.response.ResponseImageResource;
import com.megatravel.model.ImageResource;

public class ImageConverter extends AbstractConverter {

	public static ResponseImageResource toResponseFromEntity(ImageResource img) {
		return new ResponseImageResource(img.getPath());
	}

}
