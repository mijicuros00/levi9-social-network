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

    public Item(Long id, String link) {
        this.id = id;
        this.link = link;
    }

    public Item( String link) {
        this.link = link;
    }

    public Item() {
    }

   
}
