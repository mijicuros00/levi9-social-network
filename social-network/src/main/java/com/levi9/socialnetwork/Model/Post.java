package com.levi9.socialnetwork.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="post", schema="public")

public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "private")
    private boolean isPrivate;

    @Column(name = "text")
    private String text;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_group")
    private Long groupId;

    public Post(Long id, boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId) {
        this.id = id;
        this.isPrivate = isPrivate;
        this.text = text;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Post() {
    }

    public Post(boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId) {
        this.isPrivate = isPrivate;
        this.text = text;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
