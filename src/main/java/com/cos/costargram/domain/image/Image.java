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
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.costargram.domain.comment.Comment;
import com.cos.costargram.domain.likes.Likes;
import com.cos.costargram.domain.tag.Tag;
import com.cos.costargram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@JsonIgnoreProperties({"images"})
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Tag> tags;
	
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	
	// comment(댓글)
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
	// follow 정보
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@Transient // 컬럼이 만들어지지 않는다.
	private int likeCount;
	
	@Transient
	private boolean likeState;
}
