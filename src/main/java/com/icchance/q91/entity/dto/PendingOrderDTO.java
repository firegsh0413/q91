package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 掛單資料DTO
 * </p>
 * @author 6687353
 * @since 2023/8/24 10:25:27
 */
@Data
@Builder
public class PendingOrderDTO {
    /** 訂單uid */
    private Integer id;
    /** 賣方uid */
    private Integer userId;
    /** 交易數量 */
    private BigDecimal amount;
    /** 訂單狀態 */
    private Integer status;
    /** 可用收款方式 字串 */
    private String availableGatewayStr;
    /** 賣方交易方式uid 買方選擇交易方式後才寫入 */
    private Integer sellerGatewayId;
    // 買方資訊
    /** 買方uid */
    private Integer buyerId;
    /** 買方所選交易方式uid */
    private Integer buyerGatewayId;
    /** 買方訂單uid */
    private Integer orderId;
    /** 買方下單時間 */
    private LocalDateTime tradeTime;
    /** 支付憑證 */
    private String cert;
}
