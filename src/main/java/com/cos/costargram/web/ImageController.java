package com.cos.costargram.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.comment.Comment;
import com.cos.costargram.domain.image.Image;
import com.cos.costargram.service.CommentService;
import com.cos.costargram.service.ImageService;
import com.cos.costargram.service.LikeService;
import com.cos.costargram.web.dto.CMRespDto;
import com.cos.costargram.web.dto.image.ExploreReqDto;
import com.cos.costargram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

// 시작 주소: /image
@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;
	private final LikeService likeService;
	private final CommentService commentService;

	@GetMapping({ "/", "/image/feed" })
	public String feed() {

		return "image/feed";
	}

	@GetMapping("/image")
	public @ResponseBody CMRespDto<?> image(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<Image> pages = imageService.피드이미지(principalDetails.getUser().getId(), pageable);

		return new CMRespDto<>(1, pages);
	}

	@GetMapping("/image/explore")
	public String explore(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		List<ExploreReqDto> exploreImageDtos = imageService.인기사진(principalDetails.getUser().getId());

		model.addAttribute("dtos", exploreImageDtos);

		return "image/explore";
	}

	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}

	@PostMapping("/image")
	public String image(ImageReqDto imageReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		imageService.사진업로드(imageReqDto, principalDetails);

		return "redirect:/user/" + principalDetails.getUser().getId();
	}

	@PostMapping("/image/{imageId}/likes")
	public @ResponseBody CMRespDto<?> like(@PathVariable int imageId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		likeService.좋아요(imageId, principalDetails.getUser().getId());

		return new CMRespDto<>(1, null);
	}

	@DeleteMapping("/image/{imageId}/likes")
	public @ResponseBody CMRespDto<?> unLike(@PathVariable int imageId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		likeService.싫어요(imageId, principalDetails.getUser().getId());

		return new CMRespDto<>(1, null);
	}

	@PostMapping("/image/{imageId}/comment")
	public @ResponseBody CMRespDto<?> save(@PathVariable int imageId, @RequestBody String content,
			@AuthenticationPrincipal PrincipalDetails principalDetails) { // content, imageId, userId(세션)
		Comment commentEntity = commentService.댓글쓰기(principalDetails.getUser(), content, imageId);

		return new CMRespDto<>(1, commentEntity);
	}

}
