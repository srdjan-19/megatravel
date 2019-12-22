package com.megatravel.converter;

import com.megatravel.dto.response.ResponseReservation;
import com.megatravel.dto.soap.CreateReservationRequest;
import com.megatravel.model.Reservation;
import com.megatravel.model.ReservationStatus;


public class ReservationConverter extends AbstractConverter {
	
	public static ResponseReservation toResponseFromEntity(Reservation r) {
		ResponseReservation response = new ResponseReservation(r.getId(), AccommodationConverter.toResponseFromEntity(r.getAccommodation()), r.getFromDate(), r.getTillDate(), r.getStatus());
		return response;
	}
	
	public static Reservation toReservationFromRequest(CreateReservationRequest request) {
		Reservation reservation = new Reservation();
		reservation.setFromDate(request.getFromDate());
		reservation.setTillDate(request.getTillDate());
		reservation.setStatus(ReservationStatus.ON_HOLD);
		return reservation;
	}

}
