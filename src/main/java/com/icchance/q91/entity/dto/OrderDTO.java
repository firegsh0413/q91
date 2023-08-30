package com.icchance.q91.entity.dto;

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
    private Integer userId;
    /** 買方所選交易方式uid */
    private Integer buyerGatewayId;
    /** 交易數量 */
    private BigDecimal amount;
    /** 訂單狀態 */
    private Integer status;
    /** 支付憑證 */
    private String cert;
    /** 下單時間 */
    private LocalDateTime tradeTime;
    /** 截止時間 */
    private LocalDateTime cutOffTime;
    // 賣方資訊
    /** 賣方uid */
    private Integer sellerId;
    /** 賣方所選交易方式uid */
    private Integer sellerGatewayId;
    /** 對應的掛單uid */
    private Integer pendingOrderId;
}
