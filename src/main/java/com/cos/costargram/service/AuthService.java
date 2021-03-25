package com.cos.costargram.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.domain.user.UserRepository;
import com.cos.costargram.web.dto.auth.UserJoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public void 회원가입(UserJoinReqDto userJoinReqDto) {
		String rawPassword = userJoinReqDto.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userJoinReqDto.setPassword(encPassword);
		
		userRepository.save(userJoinReqDto.toEntity());
	}
}
