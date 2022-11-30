package com.levi9.socialnetwork.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "group", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "private")
    private boolean isPrivate;

    @Column(name = "id_admin")
    private Long idAdmin;

    @Column(name = "name")
    private String name;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "request", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    @JsonIgnore
    private Collection<User> userRequests = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "member", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private Set<User> members = new HashSet<>();

    public Group(boolean isPrivate, Long idAdmin, String name) {
        super();
        this.isPrivate = isPrivate;
        this.idAdmin = idAdmin;
        this.name = name;
    }

    public boolean containsUser(Long userId) {
        for (User user : getMembers()) {
            if (user.getId().equals(userId))
                return true;
        }
        return false;
    }

    public boolean containsUserRequest(Long userId) {
        for (User user : getUserRequests()) {
            if(user.getId().equals(userId))
                return true;
        }
        return false;
    }

}
