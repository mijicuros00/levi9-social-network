package com.levi9.socialnetwork.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


public class PostDTO {

    private Long id;
    private boolean isPrivate;
    private String text;
    private LocalDateTime createdDate;
    private boolean deleted;
    private Long userId;
    private Long groupId;

    public PostDTO(Long id, boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId) {
        this.id = id;
        this.isPrivate = isPrivate;
        this.text = text;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.userId = userId;
        this.groupId = groupId;
    }

    public PostDTO() {
    }

    public PostDTO(boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId) {
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
