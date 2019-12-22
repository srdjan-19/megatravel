package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Cancellation;

@Repository
public interface CancellationRepository extends JpaRepository<Cancellation, Long> {

}
