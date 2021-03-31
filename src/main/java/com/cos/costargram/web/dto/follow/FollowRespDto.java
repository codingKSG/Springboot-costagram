package com.cos.costargram.web.dto.follow;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRespDto {

	private int userId;
	private String username;
	private String profileImageUrl;
	private BigInteger followState;
	private BigInteger equalState;
}
