package com.megatravel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.megatravel.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findAllByAccommodation(long id);

	@Query(value = "SELECT * FROM maindb.user_reservations AS ur JOIN reservation AS r ON ur.reservations_id = r.id WHERE " + 
				   "r.accommodation_id = ?1 AND ur.end_user_id = ?2 AND " +
				   "r.from_date = ?3 AND r.till_date = ?4 ", nativeQuery = true)		
	Reservation findReservationWithAccommodation(long accommodationId, long clientd, Date fromDate, Date tillDtte);
	
	@Query(value = "SELECT * FROM maindb.reservation WHERE maindb.reservation.accommodation_id = ?1", nativeQuery = true)
	List<Reservation> findReservationsForAccommodation(Long id);

	@Query(value = "SELECT * FROM "
			 + "maindb.reservation JOIN maindb.user_reservations on "
			 + "maindb.reservation.id = maindb.user_reservations.reservations_id where "
			 + "maindb.user_reservations.end_user_id = ?1 and "
			 + "maindb.reservation.accommodation_id = ?2 "
			 + "ORDER BY maindb.reservation.till_date desc", nativeQuery = true)
	List<Reservation> findMyReservationsWithAccommodation(long clientId, long accommodationId);	
	
	@Query(value = "SELECT DISTINCT * FROM  maindb.reservation as res " + 
			"JOIN maindb.accommodation as acc ON res.accommodation_id = acc.id " + 
			"JOIN maindb.user_reservations as ures ON ures.reservations_id = res.id " + 
			"WHERE acc.owned_by_id = ?1 and ures.end_user_id = ?2 group by acc.id", nativeQuery = true)
	List<Reservation> mayITalkToAgent(long agentId, long clientId);	
}
