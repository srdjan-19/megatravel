package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	Address findByCity(String city);

	Address findByZip(int zip);
	
}
