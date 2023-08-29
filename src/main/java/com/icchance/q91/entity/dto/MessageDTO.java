package com.icchance.q91.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 站內訊息DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 16:17:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MessageDTO extends BaseDTO {

    private Integer id;
}
