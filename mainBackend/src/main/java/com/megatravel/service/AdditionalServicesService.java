package com.megatravel.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AdditionalServiceConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.dto.soap.DeleteAdditionalServiceRequest;
import com.megatravel.dto.soap.UpdateAdditionalServiceRequest;
import com.megatravel.exception.BadRequestException;
import com.megatravel.model.AdditionalService;
import com.megatravel.repository.AdditionalServiceRepository;

@Service
public class AdditionalServicesService {
	
	private final String CODEBOOK_ENDPOINT = "https://localhost:8443/agent-backend/booking/codebook";
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AdditionalService save(AdditionalService aService) {
		return additionalServiceRepository.save(aService);
		
	}
	
	@Transactional(readOnly = true)
	public List<AdditionalService> findAll() {
		return additionalServiceRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findById(Long id) {
		return additionalServiceRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findByName(String name) {
		return additionalServiceRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AdditionalService create(CreateAdditionalServiceRequest request) {
		if (additionalServiceRepository.findByName(request.getName()) != null)
			throw new EntityExistsException("Additional service '" + request.getName() + "' alerady exist!");
		
		AdditionalService additionalService = AdditionalServiceConverter.toEntityFromRequest(request);
		
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);

		return additionalServiceRepository.save(additionalService);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AdditionalService modify(UpdateAdditionalServiceRequest request) {
		AdditionalService additionalService = additionalServiceRepository.findByName(request.getOldName());
		
		if (additionalService == null)
			throw new EntityNotFoundException("Additional service '" + request.getOldName() + "' does not exist!");
		
		if (additionalServiceRepository.findByName(request.getNewName()) != null)
			throw new EntityExistsException("Additional service '" + request.getNewName() + "' alerady exist!");
		
		additionalService.setName(request.getNewName());

		CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);
		
		return additionalServiceRepository.save(additionalService);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Long delete(Long id) {
		AdditionalService category = additionalServiceRepository.findById(id).orElseThrow(() -> new BadRequestException("Accommodation category with id '" + id + "' does not exist!"));
		
		additionalServiceRepository.deleteById(id);
		
        DeleteAdditionalServiceRequest request = new DeleteAdditionalServiceRequest();
        request.setName(category.getName());
        
        CodebookResponse response = (CodebookResponse) soapConnector.callWebService(CODEBOOK_ENDPOINT, request);

        return id;
	}
}
