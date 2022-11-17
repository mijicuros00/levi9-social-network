package com.levi9.socialnetwork.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="item", schema="public")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "id_post")
    private Long postId;

    public Item(Long id, String link, Long postId) {
        this.id = id;
        this.link = link;
        this.postId = postId;
    }

    public Item( String link, Long postId) {
        this.link = link;
        this.postId = postId;
    }

    public Item() {
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

}
