package com.megatravel.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.megatravel.model.ImageResource;


public interface ImageRepository extends JpaRepository<ImageResource, Long> {
	
	public ImageResource findOneById(long id);
	
	@Query(value = "select img.image_id from agentLocalBase.accommodation_image img where img.accommodation_id = ?1", nativeQuery = true)
	public List<Long> getImagesId(long accId);
	
}
