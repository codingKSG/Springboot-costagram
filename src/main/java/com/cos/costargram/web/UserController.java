package com.cos.costargram.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.costargram.domain.user.User;
import com.cos.costargram.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model) {
		User userEntity = userService.회원프로필(id);
		model.addAttribute(userEntity);
		
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/profileSetting")
	public String profileSetting(@PathVariable int id) {
		return "user/profileSetting";
	}
}
