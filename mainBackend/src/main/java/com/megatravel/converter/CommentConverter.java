package com.megatravel.converter;

import com.megatravel.dto.response.ResponseComment;
import com.megatravel.dto.soap.CreateCommentRequest;
import com.megatravel.model.Comment;

public class CommentConverter extends AbstractConverter{

	public static ResponseComment toResponseFromEntity(Comment comment) {
		return new ResponseComment(comment.getId(), comment.getContent(), comment.isVisible(), comment.getPostedBy().getUsername());
	}
	
	public static Comment toEntityFromRequest(CreateCommentRequest request) {
		Comment comment = new Comment();
		comment.setContent(request.getContent());
		return comment;
	}
	
}
