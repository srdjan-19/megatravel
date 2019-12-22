package com.megatravel.service;

import java.util.List;

import javax.xml.soap.MessageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.dto.soap.CreateMessageRequest;
import com.megatravel.dto.soap.CreateMessageResponse;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.Message;
import com.megatravel.model.MessageStatus;
import com.megatravel.model.Roles;
import com.megatravel.repository.MessageRepository;

@Service
public class MessageService {
	
	private final String AGENT_APP = "https://localhost:8443/agent-backend/";
	
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
	public List<Agent> inbox() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser client = null;
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			client = userService.findEndUser(authentication.getName());
		}
		
		return userService.findMyInbox(client.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(Message message) {
		messageRepository.saveAndFlush(message);		
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Message> send(CreateMessageRequest request) {		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser client = null;
			
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			client = userService.findEndUser(authentication.getName());
		}
			
		Agent agent = userService.findAgent(request.getRecipient());
			
		if (agent == null) {
			 throw new ExceptionResponse(MessageStatus.UNKNOWN_USERNAME.name(), HttpStatus.BAD_REQUEST);
		}
			
		Message message = new Message();
		message.setAgent(agent);
		message.setClient(client);
		message.setSentBy(Roles.END_USER);
		message.setContent(request.getContent());
		message.setStatus(MessageStatus.DELIVIERED);
		
		messageRepository.save(message);
		
		try {
            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
            messageFactory.afterPropertiesSet();

            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("com.megatravel.dto.soap");
            marshaller.afterPropertiesSet();

            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);
            webServiceTemplate.afterPropertiesSet();
            
            CreateMessageResponse response = (CreateMessageResponse) soapConnector.callWebService(AGENT_APP + "booking/message", request);
			            
    		return messageRepository.findChatHistory(agent.getId(), client.getId());

        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return messageRepository.findChatHistory(agent.getId(), client.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	public MessageStatus recieve(CreateMessageRequest request) {
		EndUser client = userService.findEndUser(request.getRecipient());
		
		Agent agent = userService.findAgent(request.getSender());
		
		Message message = new Message();
		message.setClient(client);
		message.setAgent(agent);
		message.setContent(request.getContent());
		message.setSentBy(Roles.AGENT);
		message.setStatus(MessageStatus.DELIVIERED);
				
		messageRepository.save(message);
		
		return MessageStatus.DELIVIERED;
	}
}
