package com.icchance.q91.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 會員錢包DTO
 * </p>
 * @author 6687353
 * @since 2023/8/23 14:57:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceDTO {

    private Integer userId;
    /** 錢包餘額 */
    private BigDecimal balance;
    /** 可售數量 */
    private BigDecimal availableAmount;
    /** 賣單餘額 */
    private BigDecimal pendingBalance;
    /** 交易中餘額 */
    private BigDecimal tradingAmount;

}
