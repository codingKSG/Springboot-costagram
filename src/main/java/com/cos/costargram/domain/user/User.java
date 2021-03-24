package com.cos.costargram.domain.user;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costargram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 30, unique = true)
	private String username;
	
	@JsonIgnore
	private String password;
	
	private String name; // 이름
	private String website; // 웹사이트
	private String bio; // 자기소개
	private String email; // 이메일
	private String phone; // 휴대전화
	private String gender; // 성별
	
	private String profileImageUrl; // 프로필 이미지
	private String provider; // 제공자 Google, FaceBook, Naver..
	
	private String role; // USER, ADMIN
	
	@OneToMany(mappedBy = "user")
	private List<Image> images;
	
	@CreationTimestamp
	private Timestamp createDate;
}
