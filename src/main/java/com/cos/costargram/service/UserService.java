package com.cos.costargram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public User 회원프로필(int userId) {
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			return new IllegalArgumentException();
		});
		
		return userEntity;
	}
}
