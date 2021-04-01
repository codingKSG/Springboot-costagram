package com.cos.costargram.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.costargram.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails,OAuth2User{

	private User user;
	private Map<String, Object> attributes;
	private boolean isOAuth;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
		this.isOAuth = true;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
		
	@Override
	public String getName() {
		return "";
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 해당 계정 만료
	@Override
	public boolean isAccountNonExpired() {
		// 해당 계정이 만료 안됬는지
		return true;
	}

	// 로그인 반복 실패시 잠금
	@Override
	public boolean isAccountNonLocked() {
		// 잠금 되지 않았는지
		return true;
	}

	// 비밀번호 만료
	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료 안됬는지
		return true;
	}

	// 계정 활성화
	@Override
	public boolean isEnabled() {
		// 계정 활성화 됬는지
		return true;
	}

	// 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collecters = new ArrayList<>();
		collecters.add(()-> "ROLE_"+user.getRole());
		return collecters;
	}

	
}
