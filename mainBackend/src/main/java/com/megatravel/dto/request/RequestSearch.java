package com.megatravel.dto.request;

import java.util.Date;
import java.util.List;

import com.megatravel.model.AdditionalService;
import com.megatravel.model.Cancellation;

public class RequestSearch {
	
	private String destination;
	
	private Date fromDate;
	
	private Date tillDate;
	
	private int capacity;
	
	private String type;
	
	private String category;
	
	private int distance;
	
	private List<AdditionalService> additionalServices;
	
	private Cancellation cancellation;

	public RequestSearch(String destination, Date fromDate, Date tillDate, int capacity, String type, String category, int distance,
			List<AdditionalService> additionalServices, Cancellation cancellation) {
		super();
		this.destination = destination;
		this.fromDate = fromDate;
		this.tillDate = tillDate;
		this.capacity = capacity;
		this.type = type;
		this.category = category;
		this.distance = distance;
		this.additionalServices = additionalServices;
		this.cancellation = cancellation;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<AdditionalService> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<AdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public Cancellation getCancellation() {
		return cancellation;
	}

	public void setCancellation(Cancellation cancellation) {
		this.cancellation = cancellation;
	}
	
	
	

}
