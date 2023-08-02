package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.UserBalance;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceVO {

    private String address;
    private BigDecimal balance;
    private BigDecimal availableAmount;
    private BigDecimal sellBalance;
    private BigDecimal tradingAmount;

}
