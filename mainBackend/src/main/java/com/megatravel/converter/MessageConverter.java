package com.megatravel.converter;

import com.megatravel.dto.response.ResponseMessage;
import com.megatravel.model.Message;

public class MessageConverter extends AbstractConverter {
	
	public static ResponseMessage toResponseFromEntity(Message message) {
		return new ResponseMessage(message.getClient().getUsername(), message.getAgent().getUsername(), message.getContent(), message.getSentBy());
	}

}
