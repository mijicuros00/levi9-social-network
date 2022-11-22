package com.levi9.socialnetwork.Service;

import java.util.List;

import com.levi9.socialnetwork.Exception.BadRequestException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.dto.CreatePostDTO;
import com.levi9.socialnetwork.dto.PostDTO;

public interface PostService {

    public PostDTO getPostById(Long id) throws ResourceNotFoundException;
    public Long createPost(CreatePostDTO postDTO);
    public PostDTO updatePost(Long id, PostDTO postDTO) throws ResourceNotFoundException;
    public void deletePost(Long id) throws ResourceNotFoundException;
    public List<Post> getAllPostsFromGroup(Long groupId) throws ResourceNotFoundException, BadRequestException;
}
