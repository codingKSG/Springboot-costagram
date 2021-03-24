package com.cos.costargram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costargram.service.AuthService;
import com.cos.costargram.utils.Script;
import com.cos.costargram.web.auth.dto.UserJoinReqDto;

import lombok.RequiredArgsConstructor;


// 시작 주소: /auth
@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private final AuthService authService;

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "auth/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "auth/joinForm";
	}
	
	@PostMapping("/auth/join" )
	public @ResponseBody String join(UserJoinReqDto userJoinReqDto) {
		authService.회원가입(userJoinReqDto);
		return Script.href("회원 가입 성공", "/auth/loginForm");
	}
}
