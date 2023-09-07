package com.icchance.q91.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 訊息VO
 * </p>
 * @author 6687353
 * @since 2023/9/6 14:15:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO {
    /** 未讀數量 */
    private Integer count;
}
