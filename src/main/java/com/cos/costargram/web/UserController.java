package com.cos.costargram.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.follow.Follow;
import com.cos.costargram.service.FollowService;
import com.cos.costargram.service.UserService;
import com.cos.costargram.web.dto.CMRespDto;
import com.cos.costargram.web.dto.follow.FollowRespDto;
import com.cos.costargram.web.dto.user.UserProfileReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	private final FollowService followService;
	
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
	
	@GetMapping("/user/{pageUserId}/follow")
	public @ResponseBody CMRespDto<?> toUserFollow(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		List<FollowRespDto> followRespDtos = followService.팔로우리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new CMRespDto<>(1, followRespDtos);
	}
}
