package com.megatravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	public ResponseEntity<List<ResponseMessage>> sendMessage(@RequestBody CreateMessageRequest request) {
		return ResponseEntity.ok(MessageConverter.fromEntityList(messageService.send(request), (message -> MessageConverter.toResponseFromEntity(message))));
	}
	
	//retrieve agents where i have reservation
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseAgent>> inbox() {
		return ResponseEntity.ok(AgentConverter.fromEntityList(messageService.inbox(), agent -> AgentConverter.toResponseFromEntity(agent)));	
	}
	
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(value = "/history/username={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseMessage>> chatHistory(@PathVariable("username") String username) {
		return ResponseEntity.ok(MessageConverter.fromEntityList(messageService.findChatHistory(username), (message -> MessageConverter.toResponseFromEntity(message))));
		
	}
	
	
	
	
}
