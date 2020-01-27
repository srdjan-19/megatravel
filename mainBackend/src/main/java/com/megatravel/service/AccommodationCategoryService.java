package com.megatravel.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AccommodationCategoryConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.dto.soap.DeleteAccommodationCategoryRequest;
import com.megatravel.dto.soap.UpdateAccommodationCategoryRequest;
import com.megatravel.exception.BadRequestException;
import com.megatravel.model.AccommodationCategory;
import com.megatravel.repository.AccommodationCategoryRepository;

@Service
public class AccommodationCategoryService {

	private final String CODEBOOK_ENDPOINT = "https://localhost:8443/agent-backend/booking/codebook";
	
	@Autowired
	private AccommodationCategoryRepository accommodationCategoryRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationCategory save(AccommodationCategory aCategory) {
		return accommodationCategoryRepository.save(aCategory);	
	}
	
	@Transactional(readOnly = true)
	public List<AccommodationCategory> findAll() {
		return accommodationCategoryRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findById(Long id) {
		return accommodationCategoryRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findByName(String name) {
		return accommodationCategoryRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationCategory create(CreateAccommodationCategoryRequest request) {
		if (accommodationCategoryRepository.findByName(request.getName()) != null)
			throw new EntityExistsException("Accommodation category '" + request.getName() + "' alerady exist!");
		
		AccommodationCategory accommodationCategory = AccommodationCategoryConverter.toEntityFromRequest(request);
		
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
        
		return accommodationCategoryRepository.save(accommodationCategory);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationCategory modify(UpdateAccommodationCategoryRequest request) {
		AccommodationCategory category = accommodationCategoryRepository.findByName(request.getOldName());
		
		if (category == null)
			throw new EntityNotFoundException("Accommodation category '" + request.getOldName() + "' does not exist");
		
		if (accommodationCategoryRepository.findByName(request.getNewName()) != null)
			throw new EntityExistsException("Accommodation category '" + request.getNewName() + "' alerady exist!");
		
		category.setName(request.getNewName());
		
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
		
		return accommodationCategoryRepository.save(category);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Long delete(Long id) {
		AccommodationCategory category = accommodationCategoryRepository.findById(id).orElseThrow(() -> new BadRequestException("Accommodation category '" + id + "' does not exist!"));
		
		accommodationCategoryRepository.delete(category);
		
		DeleteAccommodationCategoryRequest request = new DeleteAccommodationCategoryRequest();
        request.setName(category.getName());
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
		
        return id;
	}
}
