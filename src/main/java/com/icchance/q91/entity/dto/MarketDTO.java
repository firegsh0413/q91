package com.icchance.q91.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MarketDTO {

    /** 掛單用戶UID */
    private Integer userId;
    /** 金額最小值 */
    private BigDecimal min;
    /** 金額最大值 */
    private BigDecimal max;
    /** 可用收款方式種類 */
    private List<Integer> gatewayType;
    /** 訂單UID */
    private Integer orderId;
    /** 訂單狀態 */
    private Integer status;
}
