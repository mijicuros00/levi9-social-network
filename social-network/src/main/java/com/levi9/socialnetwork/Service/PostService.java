package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.dto.PostDTO;

public interface PostService {

    public PostDTO getPostById(Long id) throws ResourceNotFoundException;
    public Long createPost(PostDTO postDTO);
    public PostDTO updatePost(Long id, PostDTO postDTO) throws ResourceNotFoundException;
    public void deletePost(Long id) throws ResourceNotFoundException;
}
