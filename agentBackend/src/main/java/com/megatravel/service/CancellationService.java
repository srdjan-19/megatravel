package com.megatravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megatravel.model.Cancellation;
import com.megatravel.repository.CancellationRepository;

@Service
public class CancellationService {
	
	@Autowired
	private CancellationRepository cancellationRepository;
	
	public Cancellation save(Cancellation cancellation) {
		return cancellationRepository.save(cancellation);
	}

}
