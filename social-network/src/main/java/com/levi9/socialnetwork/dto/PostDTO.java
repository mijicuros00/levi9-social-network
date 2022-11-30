package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.Item;
import com.levi9.socialnetwork.Model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class PostDTO {

    private Long id;
    private boolean isPrivate;
    private String text;
    private LocalDateTime createdDate;
    private boolean deleted;
    private Long userId;
    private Long groupId;

    private Set<User> hiddenFrom = new HashSet<>();

    private Set<Item> items = new HashSet<>();

    public PostDTO(Long id, boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId, Set<User> hiddenFrom, Set<Item> items) {
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

    public PostDTO(boolean isPrivate, String text, LocalDateTime createdDate, boolean deleted, Long userId, Long groupId, Set<User> hiddenFrom, Set<Item> item) {
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
