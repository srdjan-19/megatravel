package com.megatravel.service;

import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AddressConverter;
import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CreateAccommodationRequest.Pricelist;
import com.megatravel.dto.soap.CudAccommodationResponse;
import com.megatravel.dto.soap.DeleteAccommodationRequest;
import com.megatravel.dto.soap.UpdateAccommodationRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.Accommodation;
import com.megatravel.model.AccommodationCategory;
import com.megatravel.model.AccommodationType;
import com.megatravel.model.AdditionalService;
import com.megatravel.model.Agent;
import com.megatravel.model.Cancellation;
import com.megatravel.model.PriceInSeason;
import com.megatravel.repository.AccommodationRepository;
import com.megatravel.repository.AccommodationRepository;


@Service
public class AccommodationService {
	
	private final String MAIN_APP = "https://localhost:8443/main-backend/";

	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	private AccommodationTypeService accommodationTypeService;
	
	@Autowired
	private AccommodationCategoryService accommodationCategoryService;
	
	@Autowired
	private AdditionalServicesService aditionalServices;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private PriceInSeasonService priceInSeasonService;
	
	@Autowired
	private CancellationService cancellationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SOAPConnector soap;
	
	@Transactional(readOnly = true)
	public Accommodation findById(long accId) {
		return this.accommodationRepository.findById(accId);
	}
	
	@Transactional(readOnly = true)
	public Accommodation findByName(String accName) {
		return this.accommodationRepository.findByName(accName);
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAll(int page) {

		Pageable retrieve = PageRequest.of(page, 10);
		
		return accommodationRepository.findAll(retrieve).getContent();
	}
	
	
	@Transactional(readOnly = true)
	public List<Accommodation> findByCategory(AccommodationCategory categ) {
		return accommodationRepository.findByCategory(categ);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(Accommodation acc) {
		this.accommodationRepository.save(acc); 
	}
	
//	@Transactional(rollbackFor = Exception.class)
	public CudAccommodationResponse delete(long id) {
		
		Accommodation accommodation = accommodationRepository.findById(id);
		
		if (accommodation == null)
			throw new ExceptionResponse("Accommodation '" + id + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		accommodationRepository.delete(accommodation);

		
		try {
            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
            messageFactory.afterPropertiesSet();

            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("com.megatravel.dto.soap");
            marshaller.afterPropertiesSet();

            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);
            webServiceTemplate.afterPropertiesSet();
                      
            DeleteAccommodationRequest request = new DeleteAccommodationRequest();
            request.setName(accommodation.getName());
            CudAccommodationResponse response = new CudAccommodationResponse();
            response = (CudAccommodationResponse) webServiceTemplate.marshalSendAndReceive(MAIN_APP + "booking/accommodation", request);	
		   
    		accommodationRepository.delete(accommodation);

            return response;

        } catch (Exception s) {
            s.printStackTrace();
			throw new ExceptionResponse("Sync db fail!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}

	//TODO name validation; if cancellation period 0 ->  notAvailable
	@Transactional(rollbackFor = Exception.class)
	public Accommodation create(CreateAccommodationRequest request, User user) {
		Agent agent = (Agent) userService.findUser(user.getUsername());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Accommodation accommodation = new Accommodation();
		accommodation.setCapacity(request.getCapacity());
		accommodation.setCategory(accommodationCategoryService.findByName(request.getCategory()));
		accommodation.setType(accommodationTypeService.findByName(request.getType()));
		accommodation.setAddress(addressService.save(AddressConverter.toEntityFromRequest(request.getAddress())));
		accommodation.setDescription(request.getDescription());
		accommodation.setName(request.getName());
		
		accommodation.setOwnedBy(agent);
		
		for (String asName : request.getAdditionalServices()) {
			AdditionalService as = aditionalServices.findByName(asName);
			if (as == null)
				throw new ExceptionResponse("Additional service '" + asName + "' does not exist!", HttpStatus.BAD_REQUEST);
			else
				accommodation.getAdditionalServices().add(as);
		}
		
		for (Pricelist pricelist : request.getPricelist()) {
			PriceInSeason price = new PriceInSeason();
			price.setCurrency(pricelist.getCurrency());
			price.setPrice(pricelist.getPrice());
			price.setMonth(pricelist.getMonth());
			
			priceInSeasonService.save(price);
						
			accommodation.getPriceInSeason().add(price);
		}
		
		//TODO converter
		Cancellation cancellation = new Cancellation();
		cancellation.setAvailable(request.getCancellation().isAvailable());
		cancellation.setPeriod(request.getCancellation().getPeriod());
		accommodation.setCancellation(cancellationService.save(cancellation));
				
		accommodation.setAddress(addressService.save(AddressConverter.toEntityFromRequest(request.getAddress())));;
		
		return accommodationRepository.save(accommodation);
//		request.setOwner(agent.getUsername());
		

		//Object response = soap.callWebService(MAIN_APP + "booking/accommodation", request);
		//response.toString();
//		try {
//            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
//            messageFactory.afterPropertiesSet();
//
//            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
//            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//
//            marshaller.setContextPath("com.megatravel.dto.soap");
//            marshaller.afterPropertiesSet();
//
//            webServiceTemplate.setMarshaller(marshaller);
//            webServiceTemplate.setUnmarshaller(marshaller);
//            webServiceTemplate.afterPropertiesSet();
//           
//            request.setOwner("agent19");
//            
//            CudAccommodationResponse response = (CudAccommodationResponse) webServiceTemplate.marshalSendAndReceive(MAIN_APP + "booking/accommodation", request);	
//			
//            return accommodation;
//
//        } catch (Exception s) {
//            s.printStackTrace();
//        }
		
	}

	public Accommodation update(UpdateAccommodationRequest request) {

		Accommodation accommodation = accommodationRepository.findById(request.getId());
		
		if (accommodation == null)
			throw new ExceptionResponse("Accommodation with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		if (request.getNewName() != null) {
			Accommodation accommodationByName = accommodationRepository.findByName(request.getNewName());
			if (accommodationByName.getName().equals(request.getNewName()) && accommodationByName.getId() != request.getId())
				throw new ExceptionResponse("Accommodation with name '" + request.getId() + "' already exist!", HttpStatus.BAD_REQUEST);
			
			accommodation.setName(request.getNewName());
		}
		
		if (request.getCategory() != null) {
			AccommodationCategory category = accommodationCategoryService.findByName(request.getCategory());
			
			if (category == null)
				throw new ExceptionResponse("Accommodation category with name '" + request.getCategory() + "' does not exist!", HttpStatus.BAD_REQUEST);

			accommodation.setCategory(category);
		}
		
		if (request.getType() != null) {
			AccommodationType type = accommodationTypeService.findByName(request.getType());
			
			if (type == null)
				throw new ExceptionResponse("Accommodation type with name '" + request.getCategory() + "' does not exist!", HttpStatus.BAD_REQUEST);

			accommodation.setType(type);
		}
		
//		try {
//            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
//            messageFactory.afterPropertiesSet();
//
//            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
//            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//
//            marshaller.setContextPath("com.megatravel.dto.soap");
//            marshaller.afterPropertiesSet();
//
//            webServiceTemplate.setMarshaller(marshaller);
//            webServiceTemplate.setUnmarshaller(marshaller);
//            webServiceTemplate.afterPropertiesSet();
//                       
//            CudAccommodationResponse response = new CudAccommodationResponse();
//            try {
//            	 response = (CudAccommodationResponse) webServiceTemplate.marshalSendAndReceive(MAIN_APP + "booking/accommodation", request);	
//			} catch (WebServiceException e) {
//				throw new ExceptionResponse("Sync db fail!", HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//           
//            return response;
//
//        } catch (Exception s) {
//            s.printStackTrace();
//			throw new ExceptionResponse("Sync db fail!", HttpStatus.INTERNAL_SERVER_ERROR);
//        }		
		
		return accommodationRepository.save(accommodation);
	}

	@Transactional(readOnly = true)
	public List<Accommodation> findOwned() {
		Agent agent = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    agent = (Agent) userService.findUser(currentUserName);
		}
		
		return accommodationRepository.findOwned(agent.getId());
	}
}
