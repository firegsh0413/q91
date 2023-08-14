package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderDTO {
    /** 訂單uid */
    private Integer id;
    /** 買方uid */
    private Integer userId;
    /** 賣方uid */
    private Integer sellerId;
    /** 對應的掛單uid */
    private Integer pendingOrderId;
    /** 買方所選交易方式uid */
    private Integer buyerGatewayId;
    /** 賣方所選交易方式uid */
    private Integer sellerGatewayId;
    /** 交易數量 */
    private BigDecimal amount;
    /** 訂單狀態 */
    private Integer status;
    /** 支付憑證 */
    private String cert;
}
