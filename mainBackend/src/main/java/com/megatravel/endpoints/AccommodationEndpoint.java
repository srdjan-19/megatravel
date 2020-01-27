package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.converter.AccommodationConverter;
import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CudAccommodationResponse;
import com.megatravel.dto.soap.DeleteAccommodationRequest;
import com.megatravel.dto.soap.UpdateAccommodationRequest;
import com.megatravel.service.AccommodationService;

@Endpoint
public class AccommodationEndpoint {
		
	private static final String NAMESPACE_URI = "http://www.megatravel.com/accommodation";

	private final AccommodationService  accommodationService;
	
	public AccommodationEndpoint(AccommodationService accommodationService) {
		super();
		this.accommodationService = accommodationService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationRequest")
    @ResponsePayload
    public CudAccommodationResponse create(@RequestPayload CreateAccommodationRequest request) {	
		return AccommodationConverter.toCudAccommodationResponse(accommodationService.create(request), "created.");
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationRequest")
    @ResponsePayload
    public CudAccommodationResponse update(@RequestPayload UpdateAccommodationRequest request) {
		return AccommodationConverter.toCudAccommodationResponse(accommodationService.update(request), "updated.");
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationRequest")
    @ResponsePayload
    public CudAccommodationResponse delete(@RequestPayload DeleteAccommodationRequest request) {
		return AccommodationConverter.toCudAccommodationResponse(accommodationService.delete(request), " removed.");
	}
	
}