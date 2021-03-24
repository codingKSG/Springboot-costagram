package com.cos.costargram.service;

import org.springframework.stereotype.Service;

import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	
	public void 회원프로필(int userId) {
		User userEntiry = userRepository.findById(userId).orElseThrow(()->{
			return new IllegalArgumentException();
		});
	}
}
