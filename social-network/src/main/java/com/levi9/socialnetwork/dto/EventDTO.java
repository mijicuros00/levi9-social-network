package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.Address;

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
    private AddressDTO location;
    private Long userId;
    private Long groupId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Member> memberUsers;
    
    public EventDTO() {
    }

    public EventDTO(Long id, AddressDTO location, Long userId, Long groupId, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.location = location;
        this.userId = userId;
        this.groupId = groupId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDTO(Event event, Address location) {
        this.id = event.getId();
        this.location = new AddressDTO(location);
        this.userId = event.getUserId();
        this.groupId = event.getGroupId();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

}
