package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.Event;

import java.time.LocalDateTime;

public class EventDTO {
    private Long id;
    private Long locationId;
    private Long userId;
    private Long groupId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

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
