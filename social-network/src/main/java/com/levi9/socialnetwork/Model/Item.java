package com.levi9.socialnetwork.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="item", schema="public")
@Getter
@Setter
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

   
}
