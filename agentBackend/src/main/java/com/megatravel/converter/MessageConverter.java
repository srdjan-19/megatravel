package com.megatravel.converter;

import com.megatravel.dto.response.ResponseMessage;
import com.megatravel.model.Message;

public class MessageConverter extends AbstractConverter {
	
	public static ResponseMessage toResponseFromEntity(Message message) {
		return new ResponseMessage(message.getAgent().getUsername(), message.getClient().getUsername(), message.getContent(), message.getSentBy());
	}

}
