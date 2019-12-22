package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.dto.soap.DeleteAccommodationTypeRequest;
import com.megatravel.dto.soap.UpdateAccommodationTypeRequest;
import com.megatravel.model.AccommodationType;
import com.megatravel.repository.AccommodationTypeRepository;

@Service
public class AccommodationTypeService {

	@Autowired
	private AccommodationTypeRepository atRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationType save(AccommodationType aType) {
		return atRepository.save(aType);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String delete(DeleteAccommodationTypeRequest request) {
		AccommodationType accommodationType = atRepository.findByName(request.getName());
		atRepository.delete(accommodationType);
		
		return "Accommodation type '" + request.getName() + "'" + " has been deleted.";
	}
	
	@Transactional(readOnly = true)
	public List<AccommodationType> findAll() {
		return atRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findById(Long id) {
		return atRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findByName(String name) {
		return atRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String modify(UpdateAccommodationTypeRequest request) {
		AccommodationType accommodationType = atRepository.findByName(request.getOldName());
		
		if (accommodationType == null)
			return "Additional service with name '" + request.getOldName() + "'' does not exist!";

		if (atRepository.findByName(request.getNewName()) != null)
			return "Additional service with name '" + request.getNewName() + "'' already exist!";
		
		atRepository.modify(accommodationType.getId(), request.getNewName());
		
		return "Type updated from '" + request.getOldName() + "' to '" + accommodationType.getName() + "'.";
	}

	@Transactional(rollbackFor = Exception.class)
	public String create(CreateAccommodationTypeRequest request) {
		AccommodationType accommodationType = new AccommodationType();
		
		if (atRepository.findByName(request.getName()) != null)
			return "Type with name '" + request.getName() + "'' already exist!";
		
		accommodationType.setName(request.getName());
		atRepository.save(accommodationType);
		
		return "A new type has been created: " + accommodationType.getName();		
	}
	
}
