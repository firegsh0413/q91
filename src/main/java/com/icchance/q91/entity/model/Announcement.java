package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 站內公告entity
 * </p>
 * @author 6687353
 * @since 2023/8/18 15:37:24
 */
@Data
@TableName("announcement")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Integer isNoticed;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
