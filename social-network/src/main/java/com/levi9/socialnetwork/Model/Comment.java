package com.levi9.socialnetwork.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi9.socialnetwork.dto.CommentDTO;

import lombok.NoArgsConstructor;

@Entity
@Table(name="comment", schema="public")
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "text")
	private String text; 
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Column(name = "deleted")
	private boolean deleted;

	@Column(name = "id_user")
	private Long idUser;
	
	@Column(name = "id_replied_to")
	private Long idRepliedTo;
	
	@Column(name = "id_post")
	private Long idPost;

	public Comment(Long id, String text, LocalDateTime createdDate, boolean deleted, Long idUser, Long idRepliedTo,
			Long idPost) {
		super();
		this.id = id;
		this.text = text;
		this.createdDate = createdDate;
		this.deleted = deleted;
		this.idUser = idUser;
		this.idRepliedTo = idRepliedTo;
		this.idPost = idPost;
	}

	public Comment(CommentDTO commentDTO) {
		super();
		this.text = commentDTO.getText();
		this.createdDate = commentDTO.getCreatedDate();
		this.deleted = commentDTO.isDeleted();
		this.idUser = commentDTO.getIdUser();
		this.idRepliedTo = commentDTO.getIdRepliedTo();
		this.idPost = commentDTO.getIdPost();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
