package com.icchance.q91.entity.vo;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnnouncementVO {

    private Integer id;

    private String title;

    private String content;

    private LocalDateTime createTime;

}
