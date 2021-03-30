package com.cos.costargram.utils;

import java.util.ArrayList;
import java.util.List;

import com.cos.costargram.domain.image.Image;
import com.cos.costargram.domain.tag.Tag;

public class TagUtils {
	
	public static List<Tag> parsingToTagObject(String tags, Image imageEntity) {
		String temp[] = tags.split("#"); // #여행 #바다
		System.out.println("temp: "+temp);
		List<Tag> tagList = new ArrayList<>();
		
		for (int i = 1; i<temp.length; i++) {
			Tag tag = Tag.builder()
					.name(temp[i].trim())
					.image(imageEntity)
					.build();
			tagList.add(tag);
		}
		
		return tagList;
	}
}
