package com.megatravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.megatravel.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

//	@Query(value = "SELECT * FROM comment WHERE visible = ?1", nativeQuery = true)
	List<Comment> findByVisible(boolean allowed);
	
	@Query(value = "SELECT * FROM comment WHERE posted_by_id = ?1", nativeQuery = true)
	List<Comment> findByUserId(Long id);

	@Modifying
	@Query(value = "DELETE FROM comment WHERE posted_by_id = ?1", nativeQuery = true)
	void deleteByUserId(Long id);
}
	