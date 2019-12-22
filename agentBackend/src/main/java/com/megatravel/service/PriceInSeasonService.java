package com.megatravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.model.PriceInSeason;
import com.megatravel.repository.PriceInSeasonRepository;

@Service
public class PriceInSeasonService {

	@Autowired
	private PriceInSeasonRepository priceInSeasonRepository;

	@Transactional(rollbackFor = Exception.class)
	public void save(PriceInSeason price) {
		priceInSeasonRepository.save(price);
	}
	
}
