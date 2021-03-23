package com.cos.costargram.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("로그인 진행중........");
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			// 실패시 강제로 Exception 터트리기
			return null;
		} else {
			return new PrincipalDetails(userEntity); //SecurityContextHolder => Authentication 객체 내부에 담김.
		}
	}
	
}
