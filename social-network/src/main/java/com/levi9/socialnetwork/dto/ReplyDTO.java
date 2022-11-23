package com.levi9.socialnetwork.dto;

import java.time.LocalDateTime;

public class ReplyDTO {
	
	private String text;

	private LocalDateTime createdDate;
	
	private boolean deleted;

	private Long idUser;

	private Long idRepliedTo;
	
	private Long idPost;
	
	public ReplyDTO() {}

	public ReplyDTO(String text, LocalDateTime createdDate, boolean deleted, Long idUser, Long idRepliedTo,
			Long idPost) {
		super();
		this.text = text;
		this.createdDate = createdDate;
		this.deleted = deleted;
		this.idUser = idUser;
		this.idRepliedTo = idRepliedTo;
		this.idPost = idPost;
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

	public Long getIdRepliedTo() {
		return idRepliedTo;
	}

	public void setIdRepliedTo(Long idRepliedTo) {
		this.idRepliedTo = idRepliedTo;
	}

	public Long getIdPost() {
		return idPost;
	}

	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	
	
	
}
