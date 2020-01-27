package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.AgentConverter;
import com.megatravel.converter.MessageConverter;
import com.megatravel.dto.response.ResponseAgent;
import com.megatravel.dto.response.ResponseMessage;
import com.megatravel.dto.soap.CreateMessageRequest;
import com.megatravel.service.MessageService;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	//empty message validate
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> sendMessage(@RequestBody CreateMessageRequest request, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(MessageConverter.toResponseFromEntity(messageService.send(request, user.getUsername())));
	}
	
	//retrieve agents where i have reservation
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAgent>> inbox(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(AgentConverter.fromEntityList(messageService.inbox(user.getUsername()), agent -> AgentConverter.toResponseFromEntity(agent)));	
	}
	
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseMessage>> chatHistory(@PathVariable("username") String username) {
		return ResponseEntity.ok(MessageConverter.fromEntityList(messageService.findChatHistory(username), (message -> MessageConverter.toResponseFromEntity(message))));
		
	}
	
	
	
	
}
