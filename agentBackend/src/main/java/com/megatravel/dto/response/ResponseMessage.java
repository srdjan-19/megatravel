package com.megatravel.dto.response;

import com.megatravel.model.Roles;

public class ResponseMessage {
	
	private String sender;
	
	private String recipient;

	private String content;
	
	private Roles sentBy;

	public ResponseMessage() {
		
	}
	
	public ResponseMessage(String sender, String recipient, String content, Roles sentBy) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
		this.sentBy = sentBy;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Roles getSentBy() {
		return sentBy;
	}

	public void setSentBy(Roles sentBy) {
		this.sentBy = sentBy;
	}

}
