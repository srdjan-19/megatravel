package com.megatravel.dto;

import com.megatravel.model.MessageStatus;
import com.megatravel.model.Roles;

public class ResponseMessage {
	
	private String from;
	
	private String content;

	private MessageStatus status;
	
	private Roles sentBy;

	public ResponseMessage(String from, String content, MessageStatus status, Roles sentBy) {
		super();
		this.from = from;
		this.content = content;
		this.status = status;
		this.sentBy = sentBy;
	}

	public Roles getSentBy() {
		return sentBy;
	}

	public void setSentBy(Roles sentBy) {
		this.sentBy = sentBy;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

}
