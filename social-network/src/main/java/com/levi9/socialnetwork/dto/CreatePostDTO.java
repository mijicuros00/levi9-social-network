package com.levi9.socialnetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class CreatePostDTO {

    private boolean isPrivate;
    private String text;
    private LocalDateTime createdDate;
    private Long userId;
    private Long groupId;
}
