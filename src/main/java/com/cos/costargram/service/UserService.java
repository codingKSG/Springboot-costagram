package com.cos.costargram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
		
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		userProfileReqDto.setUser(userEntity);

		return userProfileReqDto;
	}
	
	public User 회원정보(int id) {
		return userRepository.findById(id).get();
	}
	
	@Transactional
	public User 회원수정(int id, User user) {
		User userEntity = userRepository.findById(id).get();
		
		userEntity.setName(user.getName());
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setEmail(user.getEmail());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		if (!(user.getPassword().isEmpty())) {
			String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
			userEntity.setPassword(encPassword);
		}
		
		userRepository.save(userEntity);
		
		return userEntity;
	}
	
}
