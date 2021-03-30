package com.cos.costargram.domain.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer>{
	
	// write (Modifying 붙여야함)
	
	@Modifying
	@Query(value="INSERT INTO follow(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	int mFollow(int fromUserId, int toUserId); // prepareStatement updateQuery() => -1, 0, 1
	
	@Modifying
	@Query(value="DELETE FROM follow WHERE fromUserId = :fromUserId And toUserId = :toUserId", nativeQuery = true)
	int mUnFollow(int fromUserId, int toUserId);

	@Query(value="SELECT COUNT(*) FROM follow WHERE fromUserId = :userId", nativeQuery = true)
	int mFollowCount(int userId);
	
	@Query(value="SELECT COUNT(*) FROM follow WHERE fromUserId = :principalId AND toUserId = :userId", nativeQuery = true)
	int mFollowState(int principalId, int userId);
	
	List<Follow> findByFromUserId(int userId);
	
}
