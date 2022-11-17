package com.levi9.socialnetwork.Service;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Repository.PostRepository;
import com.levi9.socialnetwork.dto.PostDTO;
import com.levi9.socialnetwork.mapper.PostMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO getPostById(Long id) throws ResourceNotFoundException {
            return postRepository.findPostById(id)
                    .map(PostMapper::MapEntityToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + "was not foung"));
    }

    public Long createPost(PostDTO postDTO){
        return postRepository.save(PostMapper.mapDTOToEntity(postDTO)).getId();
    }

    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) throws ResourceNotFoundException {
        Post post = postRepository.findPostById(id)
                .map(searchedPost -> searchedPost)
                .orElseThrow(() -> new ResourceNotFoundException("post with id " + " does not exists"));

        post.setText(postDTO.getText());
        post.setPrivate(postDTO.isPrivate());
        post.setCreatedDate(postDTO.getCreatedDate());
        post.setDeleted(postDTO.isDeleted());
        post.setUserId(postDTO.getUserId());
        post.setGroupId(postDTO.getGroupId());

        postRepository.save(post);

        return PostMapper.MapEntityToDTO(post);
    }

    @Transactional
    public void deletePost(Long id) throws ResourceNotFoundException {
        Post post = postRepository.findPostById(id)
                .map(deletedPost -> deletedPost)
                .orElseThrow(() -> new ResourceNotFoundException("post with id " + " does not exists"));

        post.setDeleted(true);
        postRepository.save(post);
    }
}
