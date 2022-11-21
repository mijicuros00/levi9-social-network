package com.levi9.socialnetwork.Model;

import java.time.LocalDateTime;
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

import com.levi9.socialnetwork.dto.EventDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_location")
    private Long locationId;

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_group")
    private Long groupId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE,targetEntity = Member.class)
    @JoinTable(name = "member_event",
    joinColumns = 
    @JoinColumn(name = "id_event", referencedColumnName = "id",nullable = false, updatable = false),
    inverseJoinColumns ={
    	    @JoinColumn(name="id_user", referencedColumnName="id_user"),
    	    @JoinColumn(name="id_group", referencedColumnName="id_group")
    	  })
	private Set<Member> memberUsers;
    
    public Event(EventDTO eventDTO) {
        this.id = eventDTO.getId();
        this.locationId = eventDTO.getLocationId();
        this.userId = eventDTO.getUserId();
        this.groupId = eventDTO.getGroupId();
        this.startDate = eventDTO.getStartDate();
        this.endDate = eventDTO.getEndDate();
        this.memberUsers = eventDTO.getMemberUsers();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
