package com.icchance.q91.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 交易資訊DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 16:43:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TransactionDTO extends BaseDTO {

    private Integer id;

    private String cert;
}
