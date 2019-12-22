package com.megatravel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.converter.CommentConverter;
import com.megatravel.dto.response.ResponseCommentUpdate;
import com.megatravel.dto.soap.CprdCommentResponse;
import com.megatravel.dto.soap.CreateCommentRequest;
import com.megatravel.dto.soap.UpdateCommentRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.Accommodation;
import com.megatravel.model.Comment;
import com.megatravel.model.EndUser;
import com.megatravel.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository cRepository;
	
	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public List<Comment> findAll() {
		return cRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(Comment comment) {
		cRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public Comment findById(long id) {
		return cRepository.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public CprdCommentResponse post(CreateCommentRequest request) {
		
		Date current = new Date();
		if (request.getTillDate().compareTo(current) >= 0)
			throw new ExceptionResponse("This reservation has not expired yet!", HttpStatus.BAD_REQUEST);
			
		Accommodation accommodation = accommodationService.findByName(request.getAccommodationName());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		EndUser client = null;
		if (!(authentication instanceof AnonymousAuthenticationToken))
			client = userService.findEndUser(authentication.getName());	
		
		if (reservationService.findReservationWithAccommodation(accommodation.getId(), client.getId(), request.getFromDate(), request.getTillDate()) == null)
			throw new ExceptionResponse("You dont have reservation in '" + request.getAccommodationName() + "'", HttpStatus.BAD_REQUEST);

		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setPostedBy(client);
		comment.setVisible(false);
		
		accommodation.getComments().add(comment);
		
		cRepository.save(comment);
		accommodationService.save(accommodation);
		
		CprdCommentResponse response = new CprdCommentResponse();
		response.setFeedback("Successfully created! \nReviewing comment...");
		
		return response;
	}

	@Transactional(readOnly = true)
	public List<Comment> findAllByAllowed(boolean allowed) {
		return cRepository.findByAllowed(allowed);
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseCommentUpdate accept(UpdateCommentRequest request) {
		Comment comment = cRepository.findById(request.getId()).orElse(null);
		
		if (comment == null)
			throw new ExceptionResponse("Comment with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		comment.setVisible(true);
		cRepository.save(comment);
		
		ResponseCommentUpdate response = new ResponseCommentUpdate();
		response.setApproved(CommentConverter.fromEntityList(cRepository.findByAllowed(true), c -> CommentConverter.toResponseFromEntity(c)));
		response.setRefused(CommentConverter.fromEntityList(cRepository.findByAllowed(false), c -> CommentConverter.toResponseFromEntity(c)));

		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseCommentUpdate refuse(UpdateCommentRequest request) {
		Comment comment = cRepository.findById(request.getId()).orElse(null);
		
		if (comment == null)
			throw new ExceptionResponse("Comment with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		comment.setVisible(false);
		cRepository.save(comment);
		
		ResponseCommentUpdate response = new ResponseCommentUpdate();
		response.setApproved(CommentConverter.fromEntityList(cRepository.findByAllowed(true), c -> CommentConverter.toResponseFromEntity(c)));
		response.setRefused(CommentConverter.fromEntityList(cRepository.findByAllowed(false), c -> CommentConverter.toResponseFromEntity(c)));
		
		return response;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Comment> delete(Long id) {
		Comment comment = cRepository.findById(id).orElse(null);
		
		if (comment == null)
			throw new ExceptionResponse("Comment with id '" + id + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		cRepository.delete(comment);
		
		return cRepository.findByAllowed(true);
	}
	
}