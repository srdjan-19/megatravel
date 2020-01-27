package com.megatravel.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import javax.xml.soap.MessageFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.controller.AccommodationController;
import com.megatravel.dto.soap.CreateReservationRequest;
import com.megatravel.dto.soap.CudReservationResponse;
import com.megatravel.dto.soap.UpdateReservationRequest;
import com.megatravel.exception.BadRequestException;
import com.megatravel.model.Accommodation;
import com.megatravel.model.EndUser;
import com.megatravel.model.Reservation;
import com.megatravel.model.ReservationStatus;
import com.megatravel.repository.ReservationRepository;

@Service
public class ReservationService {

	private static final Logger logger = LoggerFactory.getLogger(AccommodationController.class);
	private final String RESERVATIONS_ENDPOINT = "https://localhost:8443/agent-backend/booking/reservation";

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private AccommodationService accommodationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SOAPConnector soapConnector;

	@Transactional(readOnly = true)
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Reservation findById(long id) {
		return reservationRepository.findById(id).orElse(null);
	}


	@Transactional(rollbackFor = Exception.class)
	public void save(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	//TODO till and from cannot be before current date
	@Transactional(rollbackFor = Exception.class)
	public CudReservationResponse create(CreateReservationRequest request) {
		logger.info("Processing reservation request...");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser client = null;
		logger.info("Searching for signed in user...");
		if (!(authentication instanceof AnonymousAuthenticationToken))
			client = userService.findEndUser(authentication.getName());
		
		logger.info("Found a signed in user.");

		Accommodation accommodation = accommodationService.findByName(request.getAccommodationName());
		logger.info("Found acommodation " + request.getAccommodationName());

		logger.info("Validating reservation request...");
		if (request.getTillDate() == null || request.getFromDate() == null)
			throw new BadRequestException("Select 'from' and 'till' date, please.");
		
		if (request.getFromDate().compareTo(request.getTillDate()) >= 0)
			throw new BadRequestException("'From' date must be before 'till's!");

		if (accommodation == null)
			throw new EntityNotFoundException("Acommodation with name '" + request.getAccommodationName() + "' does not exist!");
		
		if (accommodationService.checkAvailability(request.getFromDate(), request.getTillDate(), request.getAccommodationName()) == null)
			throw new BadRequestException("Acommodation is not available in that period!");

		logger.info("Successfully validated reservation request.");
		logger.info("Completing reservation...");
		Reservation reservation = new Reservation();
		reservation.setAccommodation(accommodation);
		reservation.setStatus(ReservationStatus.ON_HOLD);
		reservation.setFromDate(request.getFromDate());
		reservation.setTillDate(request.getTillDate());
		
		reservationRepository.save(reservation);
		
		client.getReservations().add(reservation);
		userService.save(client);
				
		CudReservationResponse response = (CudReservationResponse) soapConnector.callWebService(RESERVATIONS_ENDPOINT, request);		
		
		logger.info("User with id '" + client.getId() + "' made an reservation in '" + reservation.getAccommodation().getName() +"'");

        return response;
    				
    }
	
	@Transactional(rollbackFor = Exception.class)
	public List<Reservation> cancel(UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).orElse(null);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser client = null;
		if (!(authentication instanceof AnonymousAuthenticationToken))
			client = userService.findEndUser(authentication.getName());
		
		
		if (reservation == null) {
			logger.info(Marker.ANY_NON_NULL_MARKER ,client.getFirstName() + " " + client.getLastName() + " AKA '" + client.getUsername()
			+ "' tried to cancel nonexistent reservation with id:  " + request.getId());

			throw new EntityNotFoundException("Reservation with id '" + request.getId() + "' does not exist!");
		}	
		reservation.setStatus(ReservationStatus.CANCELED);
		
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

            request.setStatus(ReservationStatus.CANCELED);
            
            CudReservationResponse response = (CudReservationResponse) soapConnector.callWebService(RESERVATIONS_ENDPOINT, request);
			            
    		reservationRepository.save(reservation);

    		return reservationRepository.findAll();
    				
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return reservationRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Reservation findReservationWithAccommodation(long accommodationId, long clientd, Date fromDate, Date tillDate) {
		return reservationRepository.findReservationWithAccommodation(accommodationId, clientd, fromDate, tillDate);
	}

	@Transactional(rollbackFor = Exception.class)
	public String update(UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).orElse(null);
		
		if (reservation == null)
			throw new EntityNotFoundException("Reservation with id '" + request.getId() + "' does not exist!");
		
		reservation.setStatus(request.getStatus());
				
		return "Reservation with id '" + request.getId() + "' has been: " + request.getStatus().name();
	}

}
