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

import com.megatravel.converter.CommentConverter;
import com.megatravel.dto.response.ResponseComment;
import com.megatravel.dto.response.ResponseCommentUpdate;
import com.megatravel.dto.soap.CprdCommentResponse;
import com.megatravel.dto.soap.CreateCommentRequest;
import com.megatravel.dto.soap.UpdateCommentRequest;
import com.megatravel.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PreAuthorize("hasRole('ROLE_END_USER')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CprdCommentResponse> post(@RequestBody CreateCommentRequest request) {
		return ResponseEntity.ok(commentService.post(request));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseComment>> all() {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.findAll(), comment -> CommentConverter.toResponseFromEntity(comment)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/approved", method = RequestMethod.GET)
	public ResponseEntity<List<ResponseComment>> allReviewed() {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.findAllByAllowed(true), comment -> CommentConverter.toResponseFromEntity(comment)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/refused", method = RequestMethod.GET)
	public ResponseEntity<List<ResponseComment>> allNotReviewed() {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.findAllByAllowed(false), comment -> CommentConverter.toResponseFromEntity(comment)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCommentUpdate> publish(@RequestBody UpdateCommentRequest request) {
		return ResponseEntity.ok(commentService.accept(request));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/refuse", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCommentUpdate> refuse(@RequestBody UpdateCommentRequest request) {
		return ResponseEntity.ok(commentService.refuse(request));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<List<ResponseComment>> deleteUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.delete(id), comment -> CommentConverter.toResponseFromEntity(comment)));
	}

}
