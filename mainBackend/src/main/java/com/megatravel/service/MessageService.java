package com.megatravel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.MessageConverter;
import com.megatravel.dto.soap.CreateMessageRequest;
import com.megatravel.dto.soap.CreateMessageResponse;
import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.Message;
import com.megatravel.model.MessageStatus;
import com.megatravel.model.User;
import com.megatravel.repository.MessageRepository;

@Service
public class MessageService {
	
	private final String MESSAGES_ENDPOINT = "https://localhost:8443/agent-backend/booking/message/";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(readOnly = true)
	public List<Message> findChatHistory(String username) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Agent agent = null;
		EndUser client = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			agent = userService.findAgent(username);
			client = userService.findEndUser(authentication.getName());
		}
		
		return messageRepository.findChatHistory(agent.getId(), client.getId());
	}

	@Transactional(readOnly = true)
	public List<Agent> inbox(String username) {
		EndUser client = userService.findEndUser(username);

		return userService.findMyInbox(client.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	public Message send(CreateMessageRequest request, String username) {		
		Message message = MessageConverter.toEntityFromSendRequest(request);
		
		Agent agent = userService.findAgent(request.getRecipient());
		
		if (agent == null) {
			 throw new EntityNotFoundException(MessageStatus.UNKNOWN_USERNAME.name());
		}
		
		message.setAgent(agent);
		
		EndUser	client = userService.findEndUser(username);
		message.setClient(client);
		
		messageRepository.save(message);
		
        CreateMessageResponse response = (CreateMessageResponse) soapConnector.callWebService(MESSAGES_ENDPOINT , request);
		
		return message;
	}

	@Transactional(rollbackFor = Exception.class)
	public MessageStatus recieve(CreateMessageRequest request) {
		Message message = MessageConverter.toEntityFromRecieveRequest(request);
		
		User client = userService.findUser(request.getRecipient());
		message.setClient(client);
		
		User agent = userService.findUser(request.getSender());
		message.setAgent(agent);
		
		messageRepository.save(message);
		
		return MessageStatus.DELIVIERED;
	}
}
