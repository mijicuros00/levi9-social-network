package com.levi9.socialnetwork.Service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.socialnetwork.Exception.ResourceNotFoundException;
import com.levi9.socialnetwork.Model.Comment;
import com.levi9.socialnetwork.dto.CommentDTO;
import com.levi9.socialnetwork.dto.ReplyDTO;

public interface CommentService {

	public List<Comment> getAllComments();
	
	public List<Comment> getCommentsByPost(Long postId);
	
	public List<Comment> getRepliesByComment(Long commentId);
	
	public Comment getCommentById(Long id) throws ResourceNotFoundException;

	public Comment createComment(CommentDTO commentDTO);
	
	public Comment replyToComment(ReplyDTO replyDTO);
	
	public Comment updateComment(Long commentId, @RequestBody CommentDTO commentDTO) throws ResourceNotFoundException;
	
	public Comment deleteComment(Long commentId) throws ResourceNotFoundException;

}
