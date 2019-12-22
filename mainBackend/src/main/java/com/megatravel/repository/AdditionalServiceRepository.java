package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.AdditionalService;

@Repository
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {

	@Modifying
	@Query(value = "UPDATE additional_service SET name = ?2 WHERE id = ?1", nativeQuery = true)
	void modifyAS(Long id, String name);
	
	AdditionalService findByName(String name);
	
}
