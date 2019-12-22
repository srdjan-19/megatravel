package com.megatravel.dto.request;

import java.util.Date;

public class RequestReservation {

	private RequestAccommodation accommodation;
    
	private Date fromDate;
	
	private Date tillDate;
	
	public RequestReservation() {
		
	}
    
	public RequestReservation(RequestAccommodation accommodation, Date fromDate, Date tillDate) {
		super();
		this.accommodation = accommodation;
		this.fromDate = fromDate;
		this.tillDate = tillDate;
	}

	public RequestAccommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(RequestAccommodation accommodation) {
		this.accommodation = accommodation;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

}
