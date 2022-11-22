package com.levi9.socialnetwork.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.BadRequestException;
import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.Model.Post;
import com.levi9.socialnetwork.Repository.CommentRepository;
import com.levi9.socialnetwork.Repository.PostRepository;
import com.levi9.socialnetwork.Service.CommentService;
import com.levi9.socialnetwork.dto.CommentDTO;
import com.levi9.socialnetwork.dto.ReplyDTO;

@Service
public class CommentServiceImpl implements CommentService {
	private static final String RESOURCE_NOT_FOUND_MESSAGE = "Comment not found for this id :: "; 
	private static final String DELETED_POST_MESSAGE = "Post is deleted.";
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
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
		return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + id));
	}

	public Comment createComment(CommentDTO commentDTO) throws ResourceNotFoundException, BadRequestException {
		// TODO: Take userId from LoggedInUser instead of DTO when Log-in feature is done
		Post post = postRepository.findById(commentDTO.getIdPost()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + commentDTO.getIdPost()));
		if(post.isDeleted()) {
			throw new BadRequestException(DELETED_POST_MESSAGE);
		}
		
		Comment comment = new Comment(commentDTO);
		return commentRepository.save(comment);
	}
	
	public Comment replyToComment(ReplyDTO replyDTO) throws ResourceNotFoundException, BadRequestException {
		Post post = postRepository.findById(replyDTO.getIdPost()).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + replyDTO.getIdPost()));
		if(post.isDeleted()) {
			throw new BadRequestException(DELETED_POST_MESSAGE);
		}
		
		Comment comment = new Comment(replyDTO);
		return commentRepository.save(comment);
	}
	
	public Comment updateComment(Long commentId, @RequestBody CommentDTO commentDTO) throws ResourceNotFoundException {
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + commentId));
		comment.setText(commentDTO.getText());
		
		return commentRepository.save(comment);
	}
	
	public Comment deleteComment(Long commentId) throws ResourceNotFoundException{
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + commentId));
		comment.setDeleted(true);
		commentRepository.save(comment);
		
		return comment;
	}
	
}
