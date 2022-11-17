package com.levi9.socialnetwork.mapper;

import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.dto.PostDTO;

public class PostMapper {

    public static PostDTO MapEntityToDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .text(post.getText())
                .isPrivate(post.isPrivate())
                .createdDate(post.getCreatedDate())
                .deleted(post.isDeleted())
                .userId(post.getUserId())
                .groupId(post.getGroupId())
                .build();
    }

    public static Post mapDTOToEntity(PostDTO post){
        return Post.builder()
                .id(post.getId())
                .text(post.getText())
                .isPrivate(post.isPrivate())
                .createdDate(post.getCreatedDate())
                .deleted(post.isDeleted())
                .userId(post.getUserId())
                .groupId(post.getGroupId())
                .build();
    }
}
