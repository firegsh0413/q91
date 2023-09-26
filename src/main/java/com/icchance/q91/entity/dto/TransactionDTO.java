package com.icchance.q91.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.icchance.q91.common.error.group.Order;
import com.icchance.q91.common.error.group.OrderVerify;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    /** 訂單uid */
    @NotNull(groups = {Order.class, OrderVerify.class})
    private Integer id;
    /** 支付憑證 */
    @NotNull(groups = OrderVerify.class)
    private String cert;
/*    *//** 是否為申訴 *//*
    @Builder.Default
    private Boolean isAppeal = Boolean.FALSE;*/
}
