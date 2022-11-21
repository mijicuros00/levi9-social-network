package com.levi9.socialnetwork.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.levi9.socialnetwork.Model.Event;
import com.levi9.socialnetwork.Model.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
    private Long id;
    private Long locationId;
    private Long userId;
    private Long groupId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Member> memberUsers;
    
    public EventDTO() {
    }

    public EventDTO(Long id, Long locationId, Long userId, Long groupId, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.locationId = locationId;
        this.userId = userId;
        this.groupId = groupId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.locationId = event.getLocationId();
        this.userId = event.getUserId();
        this.groupId = event.getGroupId();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

   
    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
