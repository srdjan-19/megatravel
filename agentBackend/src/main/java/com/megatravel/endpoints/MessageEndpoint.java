package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.dto.soap.CreateMessageRequest;
import com.megatravel.dto.soap.CreateMessageResponse;
import com.megatravel.model.MessageStatus;
import com.megatravel.service.MessageService;

@Endpoint
public class MessageEndpoint {
	
	private static final String NAMESPACE_URI = "http://www.megatravel.com/message";

	private final MessageService messageService;
			
	public MessageEndpoint(MessageService messageService) {
		super();
		this.messageService = messageService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMessageRequest")
    @ResponsePayload
    public CreateMessageResponse send(@RequestPayload CreateMessageRequest request) {
		messageService.recieve(request);		

		CreateMessageResponse response = new CreateMessageResponse();
		response.setFeedback(MessageStatus.DELIVIERED);
		
        return response;
    }

}
