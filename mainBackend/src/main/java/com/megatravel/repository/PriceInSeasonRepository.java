package com.megatravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.model.PriceInSeason;

@Repository
public interface PriceInSeasonRepository extends JpaRepository<PriceInSeason, Long> {

}
