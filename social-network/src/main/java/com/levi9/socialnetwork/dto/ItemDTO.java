package com.levi9.socialnetwork.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;


public class ItemDTO {

    public ItemDTO(Long id, String link, Long postId) {
        this.id = id;
        this.link = link;
        this.postId = postId;
    }

    public ItemDTO( String link, Long postId) {
        this.link = link;
        this.postId = postId;
    }

    public ItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    private Long id;
    private String link;
    private Long postId;
}
