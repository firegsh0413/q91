package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("ANNOUNCEMENT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @TableId
    private Integer id;

    private String title;

    private String content;

    private Integer isNoticed;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
