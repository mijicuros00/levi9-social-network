package com.levi9.socialnetwork.Model;

import com.levi9.socialnetwork.dto.EventDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Event(EventDTO eventDTO) {
        this.id = eventDTO.getId();
        this.locationId = eventDTO.getLocation().getId();
        this.userId = eventDTO.getUserId();
        this.groupId = eventDTO.getGroupId();
        this.startDate = eventDTO.getStartDate();
        this.endDate = eventDTO.getEndDate();
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
