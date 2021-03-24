package com.cos.costargram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.costargram.domain.image.Image;
import com.cos.costargram.domain.image.ImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	public List<Image> 피드이미지(int principalId) {
		// 1. principalId로 내가 팔로우하고 있는 사용자를 찾아야됨.(한개이거나 컬렉션이거나)
		// 2. SELECT * FROM image WHERE userId IN (SELECT toUserId FROM follow WHERE fromUserId = :principalId)
		//     SELECT i.* FROM image i INNER JOIN follow f ON f.toUserId = i.userId WHERE f.fromUserId = :principalId
		
		return imageRepository.feedImages(principalId);
	}
}
