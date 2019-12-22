package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.AccommodationCategory;

@Repository
public interface AccommodationCategoryRepository extends JpaRepository<AccommodationCategory, Long> {

	@Modifying
	@Query(value = "UPDATE accommodation_category SET name = ?2 WHERE id = ?1", nativeQuery = true)
	void modify(Long id, String name);

	AccommodationCategory findByName(String name);
	
}
