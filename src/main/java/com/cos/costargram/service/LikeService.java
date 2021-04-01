package com.cos.costargram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikesRepository likeRepository;

	@Transactional
	public void 좋아요(int imageId, int principalId) {
		likeRepository.mLike(imageId, principalId);
	}

	@Transactional
	public void 싫어요(int imageId, int principalId) {
		likeRepository.mUnLike(imageId, principalId);
	}

}
