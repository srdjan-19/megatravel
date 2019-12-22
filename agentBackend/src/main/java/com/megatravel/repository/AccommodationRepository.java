package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Accommodation;
import com.megatravel.model.AccommodationCategory;
import com.megatravel.model.AccommodationType;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

	List<Accommodation> findAll();

	List<Accommodation> findByCategory(AccommodationCategory category);
	
	List<Accommodation> findByType(AccommodationType type);
	
	Accommodation findById(long accId);

	Accommodation findByName(String accName);

	@Query(value = "SELECT * FROM agentdb.accommodation where owned_by_id = ?1",nativeQuery = true)
	List<Accommodation> findOwned(long id);
		
}
