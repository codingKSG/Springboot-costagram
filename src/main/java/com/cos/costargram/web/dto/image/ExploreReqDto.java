package com.cos.costargram.web.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExploreReqDto {
	private String postImageUrl;
	private Integer userId;
}
