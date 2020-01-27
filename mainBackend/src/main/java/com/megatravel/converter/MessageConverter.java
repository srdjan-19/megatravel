package com.megatravel.converter;

import com.megatravel.dto.response.ResponseMessage;
import com.megatravel.dto.soap.CreateMessageRequest;
import com.megatravel.model.Message;
import com.megatravel.model.MessageStatus;
import com.megatravel.model.Roles;

public class MessageConverter extends AbstractConverter {
	
	public static ResponseMessage toResponseFromEntity(Message message) {
		return new ResponseMessage(message.getClient().getUsername(), message.getAgent().getUsername(), message.getContent(), message.getSentBy());
	}
	
	public static Message toEntityFromSendRequest(CreateMessageRequest request) {
		Message message = new Message();
		message.setSentBy(Roles.END_USER);
		message.setContent(request.getContent());
		message.setStatus(MessageStatus.DELIVIERED);
		return message;
	}
	
	public static Message toEntityFromRecieveRequest(CreateMessageRequest request) {
		Message message = new Message();
		
		message.setSentBy(Roles.AGENT);
		message.setContent(request.getContent());
		message.setStatus(MessageStatus.DELIVIERED);	
		
		return message;	
	}

}
