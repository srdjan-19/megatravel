package com.megatravel.repository;

import java.util.Date;
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
	
	Accommodation findByName(String accName);
	
	@Query(value = "SELECT DISTINCT * FROM accommodation AS a WHERE a.id NOT IN " + 
				   "(SELECT r.accommodation_id FROM reservation AS r WHERE " + 
				   "(r.from_date <= ?1 AND r.till_date >= ?1) " + 
				   "OR (r.from_date < ?2 AND r.till_date >= ?2 ) " + 
				   "OR (?1 <= r.from_date AND ?2 >= r.from_date)) and a.name = ?3", nativeQuery = true)
	Accommodation checkAvailability(Date startDate, Date endDate, String accommodationName);

	@Query(value = "SELECT * FROM accommodation as a " + 
				   "JOIN accommodation_type as at on at.id = a.type_id " + 
				   "JOIN accommodation_category as ac on ac.id = a.category_id " + 
				   "WHERE a.name LIKE %:name% " + 
				   "AND at.name LIKE %:type% " + 
				   "AND ac.name LIKE %:category%", nativeQuery = true)
	List<Accommodation> search(String name, String type, String category);
		
}
