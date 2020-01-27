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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megatravel.converter.CommentConverter;
import com.megatravel.dto.response.ResponseComment;
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
	public ResponseEntity<ResponseComment> post(@RequestBody CreateCommentRequest request) {
		return ResponseEntity.ok(CommentConverter.toResponseFromEntity(commentService.post(request)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseComment>> findAll() {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.findAll(), comment -> CommentConverter.toResponseFromEntity(comment)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/search",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseComment>> findByStatus(@RequestParam("status") boolean status) {
		return ResponseEntity.ok(CommentConverter.fromEntityList(commentService.findAllByAllowed(status), comment -> CommentConverter.toResponseFromEntity(comment)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseComment> update(@RequestBody UpdateCommentRequest request) {
		return ResponseEntity.ok(CommentConverter.toResponseFromEntity(commentService.update(request)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResponseComment> getbyid(@PathVariable Long id) {
		return ResponseEntity.ok(CommentConverter.toResponseFromEntity(commentService.findById(id)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Long> delete(@PathVariable Long id) {
		return ResponseEntity.ok(commentService.delete(id));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePostedByUser(@PathVariable String username) {
		return ResponseEntity.ok(commentService.deletePostedByUserId(username));
	}

}
