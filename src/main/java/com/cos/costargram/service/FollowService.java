package com.cos.costargram.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.domain.follow.Follow;
import com.cos.costargram.domain.follow.FollowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;
	
	@Transactional
	public int 팔로우(int fromUserId, int toUserId) {
		return followRepository.mFollow(fromUserId, toUserId);
	}
	
	@Transactional
	public int 언팔로우(int fromUserId, int toUserId) {
		return followRepository.mUnFollow(fromUserId, toUserId);
	}
	
	@Transactional(readOnly = true)
	public List<Follow> 팔로우리스트(int userId) {
		return followRepository.findByFromUserId(userId);
	}
}
