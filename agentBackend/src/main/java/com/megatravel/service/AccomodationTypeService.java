package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megatravel.model.AccommodationType;
import com.megatravel.repository.AccommodationTypeRepository;


@Service
public class AccomodationTypeService {

	@Autowired
	AccommodationTypeRepository repository;

	public List<AccommodationType> findAll(){
		return repository.findAll();
	}
	
}
