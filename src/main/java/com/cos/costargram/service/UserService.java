package com.cos.costargram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.domain.follow.Follow;
import com.cos.costargram.domain.follow.FollowRepository;
import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;
import com.cos.costargram.web.dto.user.UserProfileReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	@Transactional(readOnly = true)
	public UserProfileReqDto 회원프로필(int userId, int principalId) {

		UserProfileReqDto userProfileReqDto = new UserProfileReqDto();
		int followValue = followRepository.mFollowState(userId, principalId);
		boolean followState;

		if (followValue == 0)
			followState = false;
		else
			followState = true;

		int followCount = followRepository.mFollowCount(userId);

		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			return new IllegalArgumentException();
		});

		userProfileReqDto.setFollowState(followState);
		userProfileReqDto.setFollowCount(followCount);
		userProfileReqDto.setImageCount(userEntity.getImages().size());
		userProfileReqDto.setUser(userEntity);

		return userProfileReqDto;
	}
}
