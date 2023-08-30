package com.icchance.q91.entity.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 交易資訊DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 16:43:43
 */
@Data
@Builder
public class TransactionDTO {

    private String token;

    private Integer id;

    private String cert;

}
