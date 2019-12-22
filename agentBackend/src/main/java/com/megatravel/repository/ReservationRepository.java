package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.megatravel.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findAllByAccommodation(long id);

	@Query(value = "SELECT * FROM agentdb.reservation AS r " + 
				   "JOIN agentdb.accommodation AS a ON r.accommodation_id = a.id " + 
				   "WHERE a.owned_by_id = ?1", nativeQuery = true)
	List<Reservation> findMyReservations(long agentId);
	
}
