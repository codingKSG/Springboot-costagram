package com.cos.costargram.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.follow.Follow;
import com.cos.costargram.domain.user.User;
import com.cos.costargram.service.FollowService;
import com.cos.costargram.service.UserService;
import com.cos.costargram.utils.Script;
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
	public String profile(@PathVariable int id, Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileReqDto userProfileReqDto = userService.회원프로필(id, principalDetails.getUser().getId());
		model.addAttribute("dto", userProfileReqDto);

		return "user/profile";
	}

	@GetMapping("/user/{id}/profileSetting")
	public String profileSetting(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (principalDetails.getUser().getId() == id) {
			return "user/profileSetting";
		} else {
			return "redirect:/user/" + principalDetails.getUser().getId();
		}
	}

	@PutMapping("/user/{id}")
	public @ResponseBody CMRespDto<?> profileUpdate(@PathVariable int id, User user,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User userEntity = userService.회원수정(id, user);
		principalDetails.setUser(userEntity);

		return new CMRespDto<>(1, null);
	}

	@GetMapping("/user/{pageUserId}/follow")
	public @ResponseBody CMRespDto<?> toUserFollow(@PathVariable int pageUserId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		List<FollowRespDto> followRespDtos = followService.팔로우리스트(principalDetails.getUser().getId(), pageUserId);

		return new CMRespDto<>(1, followRespDtos);
	}

	@PutMapping("/user/{id}/profileImageUrl")
	public @ResponseBody CMRespDto<?> profileImageUrlUpdate(@PathVariable int id, MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("파일 받기 : " + profileImageFile.getOriginalFilename());
		User userEntity = userService.회원사진업로드(profileImageFile, principalDetails);
		principalDetails.setUser(userEntity);
		return new CMRespDto<>(1, null);
	}
}
