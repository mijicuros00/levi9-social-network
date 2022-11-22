package com.levi9.socialnetwork.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.socialnetwork.Exception.BadRequestException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Model.User;
import com.levi9.socialnetwork.Repository.GroupRepository;
import com.levi9.socialnetwork.Repository.PostRepository;
import com.levi9.socialnetwork.Repository.UserRepository;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.dto.PostDTO;
import com.levi9.socialnetwork.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired 
    private GroupRepository groupRepository;
    
    @Autowired
    private UserRepository userRepository;

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
    
    public List<Post> getAllPostsFromGroup(Long groupId) throws ResourceNotFoundException, BadRequestException {
    	
    	// Hard-coded until log-in is not implemented, when it's done add id of logged in user
    	Long idLoggedUser = 5L;
    	Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("group with id " + groupId + " does not exists"));
    	User user = userRepository.findById(idLoggedUser).orElseThrow(() -> new ResourceNotFoundException("user with id " + idLoggedUser +  " does not exists"));
    		
    	// Replace this with function in Group model after git pull
    	boolean flag = false;
    	for (User u : group.getMembers()) {
			if(u.getId().equals(user.getId())) {
				flag = true;
			}
		}
    	if(!flag) {
    		throw new BadRequestException("User is not member of group.");
    	}
    			
    	List<Post> visiblePosts = postRepository.getAllPostsFromGroup(groupId);
    	
    	return visiblePosts;
    	
    }
}
