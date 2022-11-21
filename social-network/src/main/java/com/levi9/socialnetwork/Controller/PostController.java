package com.levi9.socialnetwork.Controller;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.Service.impl.PostServiceImpl;
import com.levi9.socialnetwork.dto.CreatePostDTO;
import com.levi9.socialnetwork.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private GroupService groupService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getOne(@PathVariable Long id){

        PostDTO postDTO;

        try{
            postDTO = postService.getPostById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody CreatePostDTO postDTO) throws ResourceNotFoundException {
        // Logged user will be used to set which user created post
        if(postDTO.getGroupId() != null){
            Group group = groupService.getGroupById(postDTO.getGroupId());
            if(!group.containsUser(postDTO.getUserId())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Long id;
        try{
            id = postService.createPost(postDTO);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUriString();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).body(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO){

        PostDTO updatedPost;

        try{
            updatedPost = postService.updatePost(id, postDTO);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePost(@PathVariable Long id){
        try{
            postService.deletePost(id);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
