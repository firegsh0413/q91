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
 * 站內信entity
 * </p>
 * @author 6687353
 * @since 2023/8/18 14:13:05
 */
@Data
@TableName("private_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer isRead;

    private String title;

    private String content;

    private Integer senderId;

    private String senderUsername;

    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
