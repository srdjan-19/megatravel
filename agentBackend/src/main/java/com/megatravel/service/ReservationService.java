package com.megatravel.service;

import java.util.List;
import java.util.Optional;

import javax.xml.soap.MessageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.ReservationConverter;
import com.megatravel.dto.soap.CreateReservationRequest;
import com.megatravel.dto.soap.CudReservationResponse;
import com.megatravel.dto.soap.UpdateReservationRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.Reservation;
import com.megatravel.model.ReservationStatus;
import com.megatravel.model.User;
import com.megatravel.repository.ReservationRepository;

@Service
public class ReservationService {

	private final String MAIN_APP = "https://localhost:8443/main-backend/";

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private AccommodationService accommodationService;

	@Autowired
	private SOAPConnector soapConnector;
	
	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String create(CreateReservationRequest request) {
		Reservation reservation = ReservationConverter.toReservationFromRequest(request);
		reservation.setAccommodation(accommodationService.findByName(request.getAccommodationName()));

		
		reservationRepository.save(reservation);
		
		return "Reservation request for '" + request.getAccommodationName() + "' has been sent!";
	}

	@Transactional(rollbackFor = Exception.class)
	public Reservation update(UpdateReservationRequest request, String username) {
		
		Optional<Reservation> reservation = reservationRepository.findById(request.getId());
		
		if (reservation.isPresent() == false)
			throw new ExceptionResponse("Reservation with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		reservation.get().setStatus(request.getStatus());
		
		try {
            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
            messageFactory.afterPropertiesSet();

            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("com.megatravel.model");
            marshaller.afterPropertiesSet();

            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);
            webServiceTemplate.afterPropertiesSet();

            CudReservationResponse response = (CudReservationResponse) soapConnector.callWebService(MAIN_APP + "booking/reservation", request);
		            
    		reservationRepository.save(reservation.get());
    		
            return reservation.get();   

        } catch (Exception s) {
            s.printStackTrace();
  
            return reservation.get();   
        }
		
	}

	@Transactional(rollbackFor = Exception.class)
	public Reservation findById(long id) {
		return reservationRepository.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public String update(UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).orElse(null);
		
		if (reservation == null)
			throw new ExceptionResponse("Reservation with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		reservation.setStatus(request.getStatus());
				
		reservationRepository.save(reservation);

		return "Reservation with id '" + request.getId() + "' has been: " + request.getStatus().name();
	}

	@Transactional(readOnly = true)
	public List<Reservation> findMyReservations(String username) {		
		User agent = userService.findAgent(username);
		
		return reservationRepository.findMyReservations(agent.getId());
	}
}
