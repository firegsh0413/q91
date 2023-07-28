package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用戶錢包
 * </p>
 * @author 6687353
 * @since 2023/7/28 14:36:08
 */
@Data
@TableName("USER_BALANCE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance implements Serializable {

    private static final long serialVersionUID = -972181572611244588L;

    @TableId(value = "ADDRESS")
    private String address;
    @TableField(value = "BALANCE")
    private BigDecimal balance;
    @TableField(value = "AVAILABLE_AMOUNT")
    private BigDecimal availableAmount;
    @TableField(value = "SELL_BALANCE")
    private BigDecimal sellBalance;
    @TableField(value = "TRADING_AMOUNT")
    private BigDecimal tradingAmount;

}
