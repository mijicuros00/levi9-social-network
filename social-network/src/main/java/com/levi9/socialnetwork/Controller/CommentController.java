package com.levi9.socialnetwork.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Service.CommentService;
import com.levi9.socialnetwork.dto.CommentDTO;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() throws ResourceNotFoundException {

        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable(value = "id") Long commentId)
            throws ResourceNotFoundException {

        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok().body(comment);

    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable(value = "postId") Long postId) {

        return new ResponseEntity<>(commentService.getCommentsByPost(postId), HttpStatus.OK);
    }

    @GetMapping("{commentId}/reply")
    public ResponseEntity<List<Comment>> getRepliesByComment(@PathVariable(value = "commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getRepliesByComment(commentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {

        commentService.createComment(commentDTO);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") Long commentId,
            @RequestBody CommentDTO commentDTO) throws ResourceNotFoundException {

        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok().body(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Comment comment = commentService.deleteComment(id);
        return ResponseEntity.ok().body(comment);
    }
}
