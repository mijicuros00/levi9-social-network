package com.levi9.socialnetwork.Service.impl;

import com.levi9.socialnetwork.Controller.UserController;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.PostRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.EmailService;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.dto.CreatePostDTO;
import com.levi9.socialnetwork.dto.PostDTO;
import com.levi9.socialnetwork.mapper.PostMapper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    

	private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private EmailService emailService;


    public PostDTO getPostById(Long id) throws ResourceNotFoundException {
            return postRepository.findPostById(id)
                    .map(PostMapper::MapEntityToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + "was not found"));
    }

    public Long createPost(CreatePostDTO postDTO){
    	
        List<User> notMutedUsers = userRepository.getNotMutedUsers(postDTO.getGroupId());
        
        Long id;
		
    		for (User user : notMutedUsers) 
    		{ 
    			try {
    				id = postRepository.save(PostMapper.mapCreateDTOToEntity(postDTO)).getId();
    				System.out.println("Thread id: " + Thread.currentThread().getId());
    				emailService.sendNotificaitionAsync(user);
    				return id;
    			}catch( Exception e ){
    				logger.info("Error sending email: " + e.getMessage());
    			} 
    		}
			
    	return 0L;
        
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
