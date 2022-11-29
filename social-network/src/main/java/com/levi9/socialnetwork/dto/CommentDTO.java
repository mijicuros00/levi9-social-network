package com.levi9.socialnetwork.dto;

import java.time.LocalDateTime;

import com.levi9.socialnetwork.Model.Comment;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class CommentDTO {

	private String text;

	private LocalDateTime createdDate;
	
	private boolean deleted;

	private Long idUser;
	
	private Long idPost;

	public CommentDTO() {}
	
	public CommentDTO(String text, LocalDateTime createdDate, boolean deleted, Long idUser, Long idPost) {
		super();
		this.text = text;
		this.createdDate = createdDate;
		this.deleted = deleted;
		this.idUser = idUser;
		this.idPost = idPost;
	}
	
	public CommentDTO(Comment comment) {
		this.text = comment.getText();
		this.createdDate = comment.getCreatedDate();
		this.deleted = comment.isDeleted();
		this.idUser = comment.getIdUser();
		this.idPost = comment.getIdPost();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdPost() {
		return idPost;
	}

	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	
	
}
