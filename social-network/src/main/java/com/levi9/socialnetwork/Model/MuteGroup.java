package com.levi9.socialnetwork.Model;

import com.levi9.socialnetwork.dto.MuteGroupDTO;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mute_group", schema = "public")
@NoArgsConstructor
@IdClass(MuteGroupId.class)
public class MuteGroup {
    @Id
    @Column(name = "id_user")
    private Long userId;

    @Id
    @Column(name = "id_group")
    private Long groupId;

    @Column(name = "is_permanent")
    private Boolean isPermanent;

    @Column(name = "end_of_mute")
    private LocalDateTime endOfMute;

    public MuteGroup(MuteGroupDTO muteGroupDTO) {
        this.userId = muteGroupDTO.getUserId();
        this.groupId = muteGroupDTO.getGroupId();
        this.isPermanent = muteGroupDTO.getPermanent();
        this.endOfMute = muteGroupDTO.getEndOfMute();
    }

    public MuteGroupId getMuteGroupId() {
        return new MuteGroupId(userId, groupId);
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

    public Boolean getPermanent() {
        return isPermanent;
    }

    public void setPermanent(Boolean permanent) {
        isPermanent = permanent;
    }

    public LocalDateTime getEndOfMute() {
        return endOfMute;
    }

    public void setEndOfMute(LocalDateTime endOfMute) {
        this.endOfMute = endOfMute;
    }

    @Override
    public String toString() {
        return "MuteGroup{" +
                "userId=" + userId +
                ", groupId=" + groupId +
                ", isPermanent=" + isPermanent +
                ", endOfMute=" + endOfMute +
                '}';
    }
}
