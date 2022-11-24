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

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "private")
    private boolean isPrivate;

    @Column(name = "id_admin")
    private Long idAdmin;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "request", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    @JsonIgnore
    private Collection<User> userRequests = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "member", joinColumns = @JoinColumn(name = "id_group"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private Set<User> members = new HashSet<>();

    public Group(boolean isPrivate, Long idAdmin) {
        super();
        this.isPrivate = isPrivate;
        this.idAdmin = idAdmin;
    }

    public boolean containsUser(Long userId) {
        for (User user : getMembers()) {
            if (user.getId().equals(userId))
                return true;
        }
        return false;
    }

}
