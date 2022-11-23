package com.levi9.socialnetwork.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi9.socialnetwork.dto.CommentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment", schema = "public")
@NoArgsConstructor
@Getter
@Setter
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

}
