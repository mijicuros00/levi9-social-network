package com.levi9.socialnetwork.dto;

import com.levi9.socialnetwork.Model.MuteGroup;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class MuteGroupDTO {
    private Long userId;
    private Long groupId;
    private Boolean isPermanent;
    private LocalDateTime endOfMute;

    public MuteGroupDTO() {
    }

    public MuteGroupDTO(Long userId, Long groupId, Boolean isPermanent, LocalDateTime endOfMute) {
        this.userId = userId;
        this.groupId = groupId;
        this.isPermanent = isPermanent;
        this.endOfMute = endOfMute;
    }

    public MuteGroupDTO(MuteGroup muteGroup) {
        this.userId = muteGroup.getUserId();
        this.groupId = muteGroup.getGroupId();
        this.isPermanent = muteGroup.getIsPermanent();
        this.endOfMute = muteGroup.getEndOfMute();
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
        return "MuteGroupDTO{" +
                "userId=" + userId +
                ", groupId=" + groupId +
                ", isPermanent=" + isPermanent +
                ", endOfMute=" + endOfMute +
                '}';
    }
}
