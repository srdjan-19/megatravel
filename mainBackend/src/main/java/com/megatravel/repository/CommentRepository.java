package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	@Query(value = "select * from comment where visible = ?1", nativeQuery = true)
	List<Comment> findByAllowed(boolean allowed);
	
	@Query(value = "select * from comment where posted_by_id = ?1", nativeQuery = true)
	List<Comment> findByUserId(Long id);
}
