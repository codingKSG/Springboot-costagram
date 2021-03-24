package com.cos.costargram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{
	
	@Query(value = "SELECT i.* FROM image i INNER JOIN follow f ON f.toUserId = i.userId WHERE f.fromUserId = :principalId ORDER BY createDate DESC", nativeQuery = true)
	List<Image> feedImages(int principalId);

}
