package com.icchance.q91.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.icchance.q91.common.constant.CacheConstant.Default.TIME_PATTERN;

/**
 * <p>
 * 站內信VO
 * </p>
 * @author 6687353
 * @since 2023/8/18 14:04:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageVO {

    private Integer id;

    private Integer isRead;

    private String title;

    private String content;

    private Integer senderId;

    private String senderUsername;

    private Integer type;
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime createTime;
}
