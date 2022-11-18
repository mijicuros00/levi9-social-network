package com.levi9.socialnetwork.Model;

import com.levi9.socialnetwork.dto.MuteGroupDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mute_group", schema = "public")
@NoArgsConstructor
@IdClass(MuteGroupId.class)
@Getter
@Setter
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

    public MuteGroup(Long userId, Long groupId, Boolean isPermanent, LocalDateTime endOfMute) {
        this.userId = userId;
        this.groupId = groupId;
        this.isPermanent = isPermanent;
        this.endOfMute = endOfMute;
    }

    public MuteGroup(MuteGroupDTO muteGroupDTO) {
        this.userId = muteGroupDTO.getUserId();
        this.groupId = muteGroupDTO.getGroupId();
        this.isPermanent = muteGroupDTO.getPermanent();
        this.endOfMute = muteGroupDTO.getEndOfMute();
    }

   
}
