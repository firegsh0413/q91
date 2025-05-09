package com.icchance.q91.entity.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>
 * 用戶錢包資訊VO
 * </p>
 * @author 6687353
 * @since 2023/8/24 11:54:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceVO {

    /** 錢包地址 */
    private String address;
    /** 錢包餘額 */
    private BigDecimal balance;
    /** 可售數量 */
    private BigDecimal availableAmount;
    /** 賣單餘額 */
    private BigDecimal pendingBalance;
    /** 交易中餘額 */
    private BigDecimal tradingAmount;

}
