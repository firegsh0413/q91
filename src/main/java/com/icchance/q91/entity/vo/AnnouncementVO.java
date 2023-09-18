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
 * 站內公告VO
 * </p>
 * @author 6687353
 * @since 2023/8/18 15:37:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementVO {

    private Integer id;

    private String title;

    private String content;
    @JsonFormat(pattern = TIME_PATTERN)
    private LocalDateTime createTime;

}
