package com.cos.costargram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.follow.FollowRepository;
import com.cos.costargram.domain.image.Image;
import com.cos.costargram.domain.tag.Tag;
import com.cos.costargram.domain.user.User;
import com.cos.costargram.domain.user.UserRepository;
import com.cos.costargram.utils.TagUtils;
import com.cos.costargram.web.dto.image.ImageReqDto;
import com.cos.costargram.web.dto.user.UserProfileReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional(readOnly = true)
	public UserProfileReqDto 회원프로필(int userId, int principalId) {

		UserProfileReqDto userProfileReqDto = new UserProfileReqDto();
		int followState = followRepository.mFollowState(principalId, userId);

		int followCount = followRepository.mFollowCount(userId);

		User userEntity = userRepository.findById(userId).orElseThrow(() -> {
			return new IllegalArgumentException();
		});

		userProfileReqDto.setFollowState(followState == 1);
		userProfileReqDto.setFollowCount(followCount);
		userProfileReqDto.setImageCount(userEntity.getImages().size());
		
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		userProfileReqDto.setUser(userEntity);

		return userProfileReqDto;
	}
	
	public User 회원정보(int id) {
		return userRepository.findById(id).get();
	}
	
	@Transactional
	public User 회원수정(int id, User user) {
		User userEntity = userRepository.findById(id).get();
		
		userEntity.setName(user.getName());
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setEmail(user.getEmail());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		if (!(user.getPassword().isEmpty())) {
			String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
			userEntity.setPassword(encPassword);
		}
		
		userRepository.save(userEntity);
		
		return userEntity;
	}
	
	@Value("${file.path}") // application.yml에 적혀있는 경로를 가져옴 
	private String uploadFolder;
	
	@Transactional
	public User 회원사진업로드(MultipartFile profileImageFile, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename();
//		System.out.println("파일명 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
//		System.out.println("파일패스: "+imageFilePath);
		
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalDetails.getUser().getId()).get();
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	}// 더티체킹
}
