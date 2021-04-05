package com.cos.costargram.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.costargram.config.auth.PrincipalDetails;
import com.cos.costargram.domain.image.Image;
import com.cos.costargram.domain.image.ImageRepository;
import com.cos.costargram.domain.tag.Tag;
import com.cos.costargram.domain.tag.TagRepository;
import com.cos.costargram.utils.TagUtils;
import com.cos.costargram.web.dto.image.ExploreReqDto;
import com.cos.costargram.web.dto.image.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final EntityManager em;
	
	@Value("${file.path}") // application.yml에 적혀있는 경로를 가져옴 
	private String uploadFolder;
	
	@Transactional(readOnly = true)
	public Page<Image> 피드이미지(int principalId, Pageable pageable) {
		// 1. principalId로 내가 팔로우하고 있는 사용자를 찾아야됨.(한개이거나 컬렉션이거나)
		// 2. SELECT * FROM image WHERE userId IN (SELECT toUserId FROM follow WHERE fromUserId = :principalId)
		//     SELECT i.* FROM image i INNER JOIN follow f ON f.toUserId = i.userId WHERE f.fromUserId = :principalId
		
		Page<Image> images = imageRepository.feedImages(principalId, pageable);
		
		// 좋아요 하트 색깔 로직
		images.forEach((image)->{
			
			int likeCount = image.getLikes().size();
			image.setLikeCount(likeCount);
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Transactional
	public void 사진업로드(ImageReqDto imageReqDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageReqDto.getFile().getOriginalFilename();
//		System.out.println("파일명 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
//		System.out.println("파일패스: "+imageFilePath);
		
		try {
			Files.write(imageFilePath, imageReqDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 참고 : Image 엔티티에 Tag는 주인이 아니다. Image 엔티티로 통해서 Tag를 save할 수 없다.
		
		// 1. Image 저장
		Image image = imageReqDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		// 2. Tag 저장
		List<Tag> tags = TagUtils.parsingToTagObject(imageReqDto.getTags(), imageEntity);
		tagRepository.saveAll(tags);
		
	}
	
	@Transactional(readOnly = true)
	public List<ExploreReqDto> 인기사진(int principalId) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT i.postImageUrl, i.userId ");
		sb.append("from likes l INNER JOIN image i ");
		sb.append("ON l.imageId = i.id ");
		sb.append("WHERE i.userId != ? ");
		sb.append("GROUP BY imageId ORDER BY COUNT(l.imageId) DESC");
		
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId);
		
		JpaResultMapper result = new JpaResultMapper();
		
		List<ExploreReqDto> exploreImagesEntity = result.list(query, ExploreReqDto.class);
		
		return exploreImagesEntity;
	}
}
