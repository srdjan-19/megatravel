package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAll();
	
	User findUserByUsername(String username);

	Agent findAgentByUsername(String username);

	EndUser findEndUserByUsername(String username);

	@Query(value = "select * from user where user.dtype = 'EndUser'", nativeQuery = true)
	List<EndUser> findEndUsers();	
	
	@Query(value = "select * from user where user.dtype = 'Agent'", nativeQuery = true)
	List<Agent> findAgents();

	@Query(value =  "SELECT * FROM user AS u " + 
					"JOIN message AS m ON m.client_id = u.id " + 
					"WHERE m.agent_id = :agentId " + 
					"GROUP BY u.id", nativeQuery = true)
	List<EndUser> findMyInbox(long agentId);

}
