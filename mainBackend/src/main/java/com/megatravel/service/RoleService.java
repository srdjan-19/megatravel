package com.megatravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megatravel.model.Role;
import com.megatravel.model.Roles;
import com.megatravel.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Role findByName(Roles role) {
		return roleRepository.findByName(role);
	}
	
}
