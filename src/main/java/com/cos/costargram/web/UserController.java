package com.cos.costargram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.service.UserService;
import com.cos.costargram.web.dto.user.UserProfileReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileReqDto userProfileReqDto = userService.회원프로필(id, principalDetails.getUser().getId());
		model.addAttribute("dto", userProfileReqDto);
		
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/profileSetting")
	public String profileSetting(@PathVariable int id) {
		return "user/profileSetting";
	}
}
