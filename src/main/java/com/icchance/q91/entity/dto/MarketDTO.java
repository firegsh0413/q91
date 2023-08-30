package com.icchance.q91.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 市場交易DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 15:31:21
 */
@Data
@Builder
public class MarketDTO {

    private String token;
    /** 掛單用戶UID */
    private Integer userId;
    /** 金額最小值 */
    private BigDecimal min;
    /** 金額最大值 */
    private BigDecimal max;
    /** 可用收款方式種類 */
    private List<Integer> gatewayType;
    /** 訂單UID */
    private Integer id;
    /** 訂單狀態 */
    private Integer status;
}
