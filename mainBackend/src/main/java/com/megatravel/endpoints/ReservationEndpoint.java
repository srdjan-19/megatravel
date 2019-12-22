package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.dto.soap.CudReservationResponse;
import com.megatravel.dto.soap.UpdateReservationRequest;
import com.megatravel.service.ReservationService;

@Endpoint
public class ReservationEndpoint {
	
	private static final String NAMESPACE_URI = "http://www.megatravel.com/reservation";

	private final ReservationService reservationService;
	
	public ReservationEndpoint(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateReservationRequest")
    @ResponsePayload
    public CudReservationResponse refuse(@RequestPayload UpdateReservationRequest request) {		
		return new CudReservationResponse(reservationService.update(request));
    }

}
