package com.cos.costargram.config.oauth;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("OAuth 로그인 진행중.....................");
		System.out.println("getAccessToken: "+userRequest.getAccessToken().getTokenValue());
		// 1. Access Token으로 회원정보를 받았다는 의미 (AccessToken은 userRequest가 들고 있음)
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		System.out.println("==================================================");
		System.out.println(oAuth2User.getAttributes());
		System.out.println("==================================================");
		
		// Retrofit
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		// 1. 통합 클래스 생성
		String clientName = userRequest.getClientRegistration().getClientName();

		System.out.println("OAuth 로그인 요청: "+clientName);
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(clientName.equals("Facebook")) {
			oAuth2UserInfo = new FacebookInfo(oAuth2User.getAttributes());
		}
		
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		
		UUID uuid = UUID.randomUUID();
		String endPassword = new BCryptPasswordEncoder().encode(uuid.toString());
		
		if(userEntity == null) {
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(endPassword)
					.name(oAuth2UserInfo.getName())
					.email(oAuth2UserInfo.getEmail())
					.role("USER")
					.build();
			
			userEntity = userRepository.save(user);
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		} else {
			
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
	}
}
