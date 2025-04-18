package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 站內訊息DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 16:17:53
 */
@Data
@Builder
public class MessageDTO {

    private String token;

    @NotNull
    private Integer id;
}
