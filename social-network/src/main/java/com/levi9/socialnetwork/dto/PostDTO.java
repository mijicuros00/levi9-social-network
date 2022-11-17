package com.levi9.socialnetwork.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostDTO {

    private Long id;

    private boolean isPrivate;

    private String text;

    private LocalDateTime createdDate;

    private boolean deleted;

    private Long userId;

    private Long groupId;
}
