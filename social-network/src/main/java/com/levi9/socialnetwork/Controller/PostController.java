package com.levi9.socialnetwork.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.levi9.socialnetwork.Exception.ResourceExistsException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Group;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Service.GroupService;
import com.levi9.socialnetwork.Service.PostService;
import com.levi9.socialnetwork.dto.CreatePostDTO;
import com.levi9.socialnetwork.dto.PostDTO;

@RestController
@RequestMapping(value = "api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")

    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) throws ResourceNotFoundException {

        PostDTO postDTO;
        postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/friendPosts/{userId}")
    public ResponseEntity<List<Post>> getAllPostsFromFriends(@PathVariable Long userId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(postService.getAllPostsFromFriends(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/friendPostsPublicGroups/{userId}")
    public ResponseEntity<List<Post>> getAllPostsOfMyFriendsFromPublicGroups(@PathVariable Long userId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(postService.getAllPostsOfMyFriendsFromPublicGroups(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/friendPostsPrivateGroups/{userId}")
    public ResponseEntity<List<Post>> getAllPostsOfMyFriendsFromPrivateGroups(@PathVariable Long userId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(postService.getAllPostsOfMyFriendsFromPrivateGroups(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody CreatePostDTO postDTO)
            throws ResourceNotFoundException, ResourceExistsException {
        // Logged user will be used to set which user created post

        Long id;
        id = postService.createPost(postDTO);

        String location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id)
                .toUriString();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).body(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO)
            throws ResourceNotFoundException {

        PostDTO updatedPost;
        updatedPost = postService.updatePost(id, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) throws ResourceNotFoundException {

        postService.deletePost(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
