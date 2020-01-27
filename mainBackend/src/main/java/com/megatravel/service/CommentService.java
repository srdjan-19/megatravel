package com.megatravel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CprdCommentResponse;
import com.megatravel.dto.soap.CreateCommentRequest;
import com.megatravel.dto.soap.UpdateCommentRequest;
import com.megatravel.exception.BadRequestException;
import com.megatravel.model.Accommodation;
import com.megatravel.model.Comment;
import com.megatravel.model.EndUser;
import com.megatravel.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(Comment comment) {
		commentRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public Comment findById(long id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public Comment post(CreateCommentRequest request) {
		
		Date current = new Date();
		if (request.getTillDate().compareTo(current) >= 0)
			throw new BadRequestException("This reservation has not expired yet!");
			
		Accommodation accommodation = accommodationService.findByName(request.getAccommodationName());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		EndUser client = null;
		if (!(authentication instanceof AnonymousAuthenticationToken))
			client = userService.findEndUser(authentication.getName());	
		
		if (reservationService.findReservationWithAccommodation(accommodation.getId(), client.getId(), request.getFromDate(), request.getTillDate()) == null)
			throw new BadRequestException("You dont have reservation in '" + request.getAccommodationName() + "'");

		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setPostedBy(client);
		comment.setVisible(false);
		
		accommodation.getComments().add(comment);
		
		commentRepository.save(comment);
		accommodationService.save(accommodation);
		
		CprdCommentResponse response = new CprdCommentResponse();
		response.setFeedback("Successfully created! \nReviewing comment...");
		
		return comment;
	}

	@Transactional(readOnly = true)
	public List<Comment> findAllByAllowed(boolean allowed) {
		return commentRepository.findByVisible(allowed);
	}

	@Transactional(rollbackFor = Exception.class)
	public Comment update(UpdateCommentRequest request) {
		Comment comment = commentRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException("Comment with id '" + request.getId() + "' does not exist!"));

		comment.setVisible(request.isStatus());
		commentRepository.save(comment);
		
		return comment;
	}

	@Transactional(rollbackFor = Exception.class)
	public Long delete(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment with id '" + id + "' does not exist!"));
		
		commentRepository.deleteById(id);
		
		return id;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String deletePostedByUserId(String username) {
		commentRepository.deleteByUserId(userService.findUser(username).getId());		
		return username;
	}
	
}