package com.megatravel.dto.response;

import java.util.List;

public class ResponseCommentUpdate {
	
	private List<ResponseComment> approved;
	
	private List<ResponseComment> refused;
	
	public ResponseCommentUpdate() {
		
	}

	public ResponseCommentUpdate(List<ResponseComment> approved, List<ResponseComment> refused) {
		super();
		this.approved = approved;
		this.refused = refused;
	}
	
	public List<ResponseComment> getApproved() {
		return approved;
	}

	public void setApproved(List<ResponseComment> accepted) {
		this.approved = accepted;
	}

	public List<ResponseComment> getRefused() {
		return refused;
	}

	public void setRefused(List<ResponseComment> refused) {
		this.refused = refused;
	}

}
