package com.icchance.q91.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PrivateMessageVO {

    private Integer id;

    private Integer isRead;

    private String title;

    private String content;

    private Integer senderId;

    private String senderUsername;

    private Integer type;

    private LocalDateTime createTime;
}
