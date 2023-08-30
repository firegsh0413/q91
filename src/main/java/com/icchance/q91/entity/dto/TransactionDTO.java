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
@Getter
@SuperBuilder
public class TransactionDTO extends BaseDTO {

    private Integer id;

    private String cert;

    public TransactionDTO(String token, Integer id, String cert) {
        super.setToken(token);
        this.id = id;
        this.cert = cert;
    }

}
