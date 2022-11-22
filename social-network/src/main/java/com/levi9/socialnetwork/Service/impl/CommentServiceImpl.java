package com.levi9.socialnetwork.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Repository.CommentRepository;
import com.levi9.socialnetwork.Service.CommentService;
import com.levi9.socialnetwork.dto.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	

	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}
	
	public List<Comment> getCommentsByPost(Long postId) {
			
		return commentRepository.getCommentsByPost(postId);
	}
	
	
	public List<Comment> getRepliesByComment(Long commentId) {
		return commentRepository.getRepliesByComment(commentId);
	}
	
	
	
	public Comment getCommentById(Long id) throws ResourceNotFoundException {
		return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));
	}

	public Comment createComment(CommentDTO commentDTO) {
		Comment comment = new Comment(commentDTO);
		return commentRepository.save(comment);
	}
	
	public Comment updateComment(Long commentId, @RequestBody CommentDTO commentDTO) throws ResourceNotFoundException {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + commentId));
		comment.setText(commentDTO.getText());
		comment.setDeleted(commentDTO.isDeleted());
		Comment updatedComment = commentRepository.save(comment);
		
		return updatedComment;
	}
	
	public Comment deleteComment(Long commentId) throws ResourceNotFoundException{
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + commentId));
		commentRepository.delete(comment);
		
		return comment;
	}
	
}
