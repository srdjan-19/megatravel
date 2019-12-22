package com.megatravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.model.Address;
import com.megatravel.repository.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	@Transactional(readOnly = true)
	public List<Address> findAll(){
		return addressRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Address findByZip(int zip) {
		return addressRepository.findByZip(zip);
	}
	
	@Transactional(readOnly = true)
	public Address findByCity(String city) {
		return addressRepository.findByCity(city);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Address save(Address adresa) {
		return addressRepository.save(adresa);
	}
}
