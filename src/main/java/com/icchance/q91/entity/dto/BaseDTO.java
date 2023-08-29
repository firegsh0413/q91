package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 基本DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 11:10:48
 */
@Data
@SuperBuilder
public class BaseDTO {

    private String token;
}
