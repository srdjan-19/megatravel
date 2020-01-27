package com.megatravel.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CreateAccommodationRequest.Pricelist;
import com.megatravel.dto.soap.DeleteAccommodationRequest;
import com.megatravel.dto.soap.UpdateAccommodationRequest;
import com.megatravel.exception.BadRequestException;
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
	private final Logger logger = LoggerFactory.getLogger(AccommodationService.class);
	
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
	
	@Transactional(readOnly = true)
	public Accommodation findById(long id) {
		return accommodationRepository.findById(id).orElseThrow(() -> new BadRequestException("Accommodation with id '" + id + "' does not exist!"));
	}
	
	@Transactional(readOnly = true)
	public Accommodation findByName(String accName) {
		return accommodationRepository.findByName(accName);
	}
	
	@Transactional(readOnly = true)
	public List<Accommodation> findAll(int page) {
		Pageable retrieve = PageRequest.of(page, 15);
		
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
	
	@Transactional(readOnly = true)
	public Accommodation checkAvailability(Date startDate, Date endDate, String accommodationName) {
		return this.accommodationRepository.checkAvailability(startDate, endDate, accommodationName);
	}
	
	//TODO name validation
	@Transactional(rollbackFor = Exception.class)
	public Accommodation create(CreateAccommodationRequest request) {
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
				throw new EntityNotFoundException("Additional service '" + asName + "' does not exist!");
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
		
		return accommodationRepository.save(accommodation);
	}

	@Transactional(rollbackFor = Exception.class)
	public Accommodation update(UpdateAccommodationRequest request) {

		Accommodation accommodation = accommodationRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException("Accommodation with id '" + request.getId() + "' does not exist!"));
		
		
		if (request.getNewName() != null) {
			if (accommodationRepository.findByName(request.getNewName()) != null)
				throw new EntityExistsException("Accommodation with name '" + request.getId() + "' already exist!");
			
			accommodation.setName(request.getNewName());
		}
		
		if (request.getCategory() != null) {
			AccommodationCategory category = accommodationCategoryService.findByName(request.getCategory());
			
			if (category == null)
				throw new EntityNotFoundException("Accommodation category with name '" + request.getCategory() + "' does not exist!");

			accommodation.setCategory(category);
		}
		
		if (request.getType() != null) {
			AccommodationType type = accommodationTypeService.findByName(request.getType());
			
			if (type == null)
				throw new EntityNotFoundException("Accommodation type with name '" + request.getCategory() + "' does not exist!");

			accommodation.setType(type);
		}
		
		return accommodation;
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Accommodation delete(DeleteAccommodationRequest request) {
//		Accommodation accommodation = accommodationRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException("Accommodation with id '" + request.getId() + "' does not exist!"));
		Accommodation accommodation = accommodationRepository.findByName(request.getName());
		accommodationRepository.delete(accommodation);
		
		return accommodation;
	}

	@Transactional(readOnly = true)
	public List<Accommodation> search(String name, String type, String category) {
		return accommodationRepository.search(name, type, category);
	}
	
}
