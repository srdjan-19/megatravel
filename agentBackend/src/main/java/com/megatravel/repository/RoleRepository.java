package com.megatravel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Role;
import com.megatravel.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(Roles role);
	
}
