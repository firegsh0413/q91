package com.icchance.q91.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 訂單資訊DTO
 * </p>
 * @author 6687353
 * @since 2023/8/24 18:04:59
 */
@Data
@Builder
public class OrderDTO{

    private String token;
    /** 訂單uid */
    private Integer id;
    /** 買方uid */
    @JsonIgnore
    private Integer userId;
    /** 買方所選交易方式uid */
    @JsonIgnore
    private Integer buyerGatewayId;
    /** 交易數量 */
    @JsonIgnore
    private BigDecimal amount;
    /** 訂單狀態 */
    @JsonIgnore
    private Integer status;
    /** 支付憑證 */
    private String cert;
    /** 下單時間 */
    @JsonIgnore
    private LocalDateTime tradeTime;
    /** 截止時間 */
    @JsonIgnore
    private LocalDateTime cutOffTime;
    // 賣方資訊
    /** 賣方uid */
    @JsonIgnore
    private Integer sellerId;
    /** 賣方所選交易方式uid */
    @JsonIgnore
    private Integer sellerGatewayId;
    /** 對應的掛單uid */
    @JsonIgnore
    private Integer pendingOrderId;
}
