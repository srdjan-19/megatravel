package com.megatravel.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AccommodationTypeConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.dto.soap.DeleteAccommodationTypeRequest;
import com.megatravel.dto.soap.UpdateAccommodationTypeRequest;
import com.megatravel.exception.BadRequestException;
import com.megatravel.model.AccommodationType;
import com.megatravel.repository.AccommodationTypeRepository;

@Service
public class AccommodationTypeService {

	private final String CODEBOOK_ENDPOINT = "https://localhost:8443/agent-backend/booking/codebook";
	
	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationType save(AccommodationType aType) {
		return accommodationTypeRepository.save(aType);
		
	}
		
	@Transactional(readOnly = true)
	public List<AccommodationType> findAll() {
		return accommodationTypeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findById(Long id) {
		return accommodationTypeRepository.findById(id).orElseThrow(() -> new BadRequestException("Accommodation type with id: " + id + " does not exists!"));
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findByName(String name) {
		return accommodationTypeRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationType create(CreateAccommodationTypeRequest request) {
		if (accommodationTypeRepository.findByName(request.getName()) != null)
			throw new EntityExistsException("Accommodation type '" + request.getName() + "' alerady exist!");
		
		AccommodationType accommodationType = AccommodationTypeConverter.toEntityFromRequest(request);
		
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
		                		
		return accommodationTypeRepository.save(accommodationType);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationType modify(UpdateAccommodationTypeRequest request) {
		AccommodationType type = accommodationTypeRepository.findByName(request.getOldName());
		
		if (type == null)
			throw new EntityNotFoundException("Accommodation type '" + request.getOldName() + "' does not exist!");
		
		if (accommodationTypeRepository.findByName(request.getNewName()) != null)
			throw new EntityExistsException("Accommodation type '" + request.getNewName() + "' alerady exist!");
		
		type.setName(request.getNewName());

        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
		
		return accommodationTypeRepository.save(type);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Long delete(Long id) {
		AccommodationType category = accommodationTypeRepository.findById(id).orElseThrow(() -> new BadRequestException("Accommodation category withd id '" + id + "' does not exist!"));
		
		accommodationTypeRepository.deleteById(id);
		
		DeleteAccommodationTypeRequest request = new DeleteAccommodationTypeRequest();
        request.setName(category.getName());
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
				
		return id;
	}
	
}
