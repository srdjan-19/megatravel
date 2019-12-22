package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.megatravel.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAll();
	
	@Query(value = "SELECT * FROM agentdb.message WHERE message.agent_id = ?1 and message.client_id = ?2", nativeQuery = true)
	List<Message> findChatHistory(Long agentId, Long clientId);
	
	@Query(value = "SELECT * FROM agentdb.message WHERE message.agent_id = ?1", nativeQuery = true)
	List<Message> findMyInbox(Long agentId);

}

