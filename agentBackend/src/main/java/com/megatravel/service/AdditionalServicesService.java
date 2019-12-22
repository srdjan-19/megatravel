package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.dto.soap.DeleteAdditionalServiceRequest;
import com.megatravel.dto.soap.UpdateAdditionalServiceRequest;
import com.megatravel.model.AdditionalService;
import com.megatravel.repository.AdditionalServiceRepository;

@Service
public class AdditionalServicesService {
	
	@Autowired
	private AdditionalServiceRepository asRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public AdditionalService save(AdditionalService aService) {
		return asRepository.save(aService);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String delete(DeleteAdditionalServiceRequest request) {
		AdditionalService additionalService = asRepository.findByName(request.getName());
		asRepository.delete(additionalService);
		
		return "Additional service '" + request.getName() + "'" + " has been deleted.";
	}
	
	@Transactional(readOnly = true)
	public List<AdditionalService> findAll() {
		return asRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findById(Long id) {
		return asRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findByName(String name) {
		return asRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String modify(UpdateAdditionalServiceRequest request) {
		AdditionalService additionalService = asRepository.findByName(request.getOldName());
		
		if (additionalService == null)
			return "Additional service with name '" + request.getOldName() + "'' does not exist!";

		if (asRepository.findByName(request.getNewName()) != null)
			return "Additional service with name '" + request.getNewName() + "'' already exist!";

		asRepository.modify(additionalService.getId(), request.getNewName());
		
		return "Additional service updated from '" + request.getOldName() + "' to '" + additionalService.getName() + "'.";
	}

	@Transactional(rollbackFor = Exception.class)
	public String create(CreateAdditionalServiceRequest request) {
		AdditionalService additionalService = new AdditionalService();
		additionalService.setName(request.getName());
		
		if (asRepository.findByName(request.getName()) != null)
			return "Additional service with name '" + request.getName() + "'' already exist!";
		
		asRepository.save(additionalService);
		
		return "A new additional service has been created: " + additionalService.getName();		
	}

}
