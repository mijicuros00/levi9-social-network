package com.levi9.socialnetwork.mapper;

import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.dto.CreatePostDTO;
import com.levi9.socialnetwork.dto.PostDTO;

public class PostMapper {

    public static PostDTO MapEntityToDTO(Post post){
        return new PostDTO(post.getId(), post.isPrivate(), post.getText(), post.getCreatedDate(), post.isDeleted(), post.getUserId(), post.getGroupId(), post.getHiddenFrom(), post.getItems());
    }

    public static Post mapDTOToEntity(PostDTO post){
        return new Post(post.getId(), post.isPrivate(), post.getText(), post.getCreatedDate(), post.isDeleted(), post.getUserId(), post.getGroupId(), post.getHiddenFrom(), post.getItems());
    }

    public static Post mapCreateDTOToEntity(CreatePostDTO post, Long userId){
        return new Post(post.isPrivate(), post.getText(), post.getCreatedDate(), false, userId, post.getGroupId(), post.getHiddenFrom(), post.getItems());
    }
}
