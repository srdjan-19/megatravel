package com.megatravel.service;

import java.util.Date;
import java.util.List;

import org.jboss.logging.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CreateAccommodationRequest.Pricelist;
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


@Service
public class AccommodationService {
	
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
	
	private final Logger logger = LoggerFactory.getLogger(AccommodationService.class);
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public Accommodation findById(long accId) {
		return this.accommodationRepository.findById(accId);
	}
	
	@Transactional(readOnly = true)
	public Accommodation findByName(String accName) {
		return this.accommodationRepository.findByName(accName);
	}
	
	//TODO retrieve only visible comments
	@Transactional(readOnly = true)
	public List<Accommodation> findAll() {
		return this.accommodationRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findByCategory(AccommodationCategory categ) {
		return accommodationRepository.findByCategory(categ);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(Accommodation acc) {
		this.accommodationRepository.save(acc); 
	}
	
	@Transactional(readOnly = true)
	public Accommodation checkAvailability(Date startDate, Date endDate, String accommodationName) {
		return this.accommodationRepository.checkAvailability(startDate, endDate, accommodationName);
	}

	@Transactional(rollbackFor = Exception.class)
	public String delete(DeleteAccommodationRequest request) {
		Accommodation accommodation = accommodationRepository.findByName(request.getName());
		
		if (accommodation == null)
			throw new ExceptionResponse("Accommodation '" + request.getName() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		accommodationRepository.delete(accommodation);
		
        return "Accommodation '" + request.getName() + "' has been deleted!";
		
	}

	//TODO name validation
	@Transactional(rollbackFor = Exception.class)
	public String create(CreateAccommodationRequest request) {
		Agent agent = userService.findAgent(request.getOwner());
		
		Accommodation accommodation = new Accommodation();
		accommodation.setCapacity(request.getCapacity());
		accommodation.setCategory(accommodationCategoryService.findByName(request.getCategory()));
		accommodation.setType(accommodationTypeService.findByName(request.getType()));
		accommodation.setAddress(addressService.findByCity(request.getCity()));
		accommodation.setDescription(request.getDescription());		
		accommodation.setDistance(request.getDistance());
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
		
		Cancellation cancellation = new Cancellation();
		cancellation.setAvailable(request.getCancellation().isAvailable());
		cancellation.setPeriod(request.getCancellation().getPeriod());
		cancellationService.save(cancellation);
		
		accommodation.setCancellation(cancellation);
		
		accommodationRepository.save(accommodation);

		return "Accommodation '" + accommodation.getName() + "' has been created!";
	}

	@Transactional(rollbackFor = Exception.class)
	public String update(UpdateAccommodationRequest request) {

		Accommodation accommodation = accommodationRepository.findById(request.getId());
		
		if (accommodation == null)
			throw new ExceptionResponse("Accommodation with id '" + request.getId() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		if (request.getNewName() != null) {
			if (accommodationRepository.findByName(request.getNewName()) != null)
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
		
		return "Accommodation with id '" + accommodation.getId() + "' has been updated!";
		
	}
}
