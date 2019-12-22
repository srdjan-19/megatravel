package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.AccommodationType;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
	
	@Modifying
	@Query(value = "UPDATE accommodation_type SET name = ?2 WHERE id = ?1", nativeQuery = true)
	void modify(Long id, String name);
	
	AccommodationType findByName(String name);

}
