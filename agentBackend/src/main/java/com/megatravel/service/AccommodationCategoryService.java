package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.dto.soap.DeleteAccommodationCategoryRequest;
import com.megatravel.dto.soap.UpdateAccommodationCategoryRequest;
import com.megatravel.model.AccommodationCategory;
import com.megatravel.repository.AccommodationCategoryRepository;

@Service
public class AccommodationCategoryService {

	@Autowired
	private AccommodationCategoryRepository acRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationCategory save(AccommodationCategory aCategory) {
		return acRepository.save(aCategory);	
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String delete(DeleteAccommodationCategoryRequest request) {
		AccommodationCategory accommodationCategory = acRepository.findByName(request.getName());
		acRepository.delete(accommodationCategory);
		
		return "Accommodation category '" + request.getName() + "'" + " has been deleted.";
	}
	
	@Transactional(readOnly = true)
	public List<AccommodationCategory> findAll() {
		return acRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findById(Long id) {
		return acRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findByName(String name) {
		return acRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String modify(UpdateAccommodationCategoryRequest request) {
		AccommodationCategory accommodationCategory = acRepository.findByName(request.getOldName());
		
		if (accommodationCategory == null)
			return "Additional service with name '" + request.getOldName() + "'' does not exist!";

		if (acRepository.findByName(request.getNewName()) != null)
			return "Additional service with name '" + request.getNewName() + "'' already exist!";
		
		acRepository.modify(accommodationCategory.getId(), request.getNewName());
		
		return "Category updated from '" + request.getOldName() + "' to '" + accommodationCategory.getName() + "'.";
	}

	@Transactional(rollbackFor = Exception.class)
	public String create(CreateAccommodationCategoryRequest request) {
		AccommodationCategory accommodationCategory = new AccommodationCategory();
		
		if (acRepository.findByName(request.getName()) != null)
			return "Category with name '" + request.getName() + "'' already exist!";
		
		accommodationCategory.setName(request.getName());
		acRepository.save(accommodationCategory);
		
		return "A new category has been created: " + accommodationCategory.getName();	
	}
	
}
