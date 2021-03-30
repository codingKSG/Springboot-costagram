package com.cos.costargram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		int followState = followRepository.mFollowState(principalId, userId);

		int followCount = followRepository.mFollowCount(userId);

		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			return new IllegalArgumentException();
		});

		userProfileReqDto.setFollowState(followState == 1);
		userProfileReqDto.setFollowCount(followCount);
		userProfileReqDto.setImageCount(userEntity.getImages().size());
		userProfileReqDto.setUser(userEntity);

		return userProfileReqDto;
	}
	
}
