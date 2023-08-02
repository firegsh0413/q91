package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 掛單資料
 * </p>
 * @author 6687353
 * @since 2023/7/28 16:21:17
 */
@Data
@TableName("PENDING_ORDER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrder {

    @TableId(value = "ID")
    private Integer id;
    @TableField(value = "USER_ID")
    private Integer userId;
    @TableField(value = "STATUS")
    private Integer status;
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;
    @TableField(value = "TRADE_TIME")
    private LocalDateTime tradeTime;
    @TableField(value = "BUYER_ID")
    private Integer buyerId;
    private Integer buyerGatewayId;
    @TableField(value = "BUYER_USERNAME")
    private String buyerUsername;
    @TableField(value = "AMOUNT")
    private BigDecimal amount;
    @TableField(value = "ORDER_NUMBER")
    private String orderNumber;
    @TableField(value = "CERT")
    private String cert;

}
