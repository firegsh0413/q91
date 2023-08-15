package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PendingOrderDTO {
    /** 訂單uid */
    private Integer id;
    /** 賣方uid */
    private Integer userId;
    /** 買方uid */
    private Integer buyerId;
    /** 買方所選交易方式uid */
    private Integer buyerGatewayId;
    /** 賣方所選交易方式uid */
    private Integer sellerGatewayId;
    /** 交易數量 */
    private BigDecimal amount;
    /** 訂單狀態 */
    private Integer status;
    /** 可用收款方式 字串 */
    private String availableGatewayStr;
}
