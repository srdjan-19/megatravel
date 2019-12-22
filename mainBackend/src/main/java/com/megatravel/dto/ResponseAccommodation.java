package com.megatravel.dto;

import java.util.List;

import com.megatravel.dto.response.ResponseAdditionalService;
import com.megatravel.dto.response.ResponseAddress;
import com.megatravel.dto.response.ResponseImageResource;

public class ResponseAccommodation {

	private String name;
    
	private ResponseAccommodationType type;
    
	private ResponseAccommodationCategory category;
    
	private ResponseAgent ownedBy;
    
	private double distance;
    
	private String description;
    
	private List<ResponseImageResource> images;
    
	private ResponseAddress address;
    
	private int capacity;
        
	private List<ResponseAdditionalService> additionalServices;
    
	private ResponseCancellation cancellation;

	private List<ResponsePriceInSeason> priceInSeason;
    
	private double rate;
    
	private List<ResponseComment> comments;
	
	public ResponseAccommodation(String name, ResponseAccommodationType type, ResponseAccommodationCategory category, double distance, String description, int capacity) {
		super();
		this.name = name;
		this.type = type;
		this.category = category;
		this.distance = distance;
		this.description = description;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResponseAccommodationType getType() {
		return type;
	}

	public void setType(ResponseAccommodationType type) {
		this.type = type;
	}

	public ResponseAccommodationCategory getCategory() {
		return category;
	}

	public void setCategory(ResponseAccommodationCategory category) {
		this.category = category;
	}

	public ResponseAgent getOwnedBy() {
		return ownedBy;
	}

	public void setOwnedBy(ResponseAgent ownedBy) {
		this.ownedBy = ownedBy;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ResponseImageResource> getImage() {
		return images;
	}

	public void setImages(List<ResponseImageResource> images) {
		this.images = images;
	}

	public ResponseAddress getAddress() {
		return address;
	}

	public void setAddress(ResponseAddress address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<ResponseAdditionalService> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalService(List<ResponseAdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public void setCancellation(ResponseCancellation cancellation) {
		this.cancellation = cancellation;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<ResponseComment> getComments() {
		return this.comments;
	}

	public void setComments(List<ResponseComment> comments) {
		this.comments = comments;
	}

	public List<ResponsePriceInSeason> getPriceInSeason() {
		return this.priceInSeason;
	}

	public void setPriceInSeason(List<ResponsePriceInSeason> priceInSeason) {
		this.priceInSeason = priceInSeason;
	}

	public List<ResponseImageResource> getImages() {
		return this.images;
	}

	public ResponseCancellation getCancellation() {
		return this.cancellation;
	}

	public void setAdditionalServices(List<ResponseAdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}
}
