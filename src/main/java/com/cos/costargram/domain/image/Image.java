package com.cos.costargram.domain.image;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costargram.domain.likes.Likes;
import com.cos.costargram.domain.tag.Tag;
import com.cos.costargram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String caption; // 작성자의 글
	private String postImageUrl;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy="image")
	private List<Tag> tags;
	
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	
	// comment(댓글)
	
	// follow 정보
	
	@CreationTimestamp
	private Timestamp createDate;
}