package com.levi9.socialnetwork.Model;

import java.io.Serializable;

public class MuteGroupId implements Serializable {
    private Long userId;
    private Long groupId;

    public MuteGroupId(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "userId=" + userId +
                ", groupId=" + groupId;
    }
}
