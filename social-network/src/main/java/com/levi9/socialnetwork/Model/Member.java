package com.levi9.socialnetwork.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member", schema = "public")
@IdClass(MemberId.class)
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "id_user")
    Long idUser;
    @Id
    @Column(name = "id_group")
    Long idGroup;

}
