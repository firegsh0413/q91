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
public class PendingOrder implements Serializable {

    private static final long serialVersionUID = -736909599601864457L;

    @TableId(value = "ID")
    private Integer id;

    private Integer userId;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime tradeTime;

    private Integer buyerId;

    private Integer buyerGatewayId;

    //private String buyerUsername;

    private BigDecimal amount;

    private String orderNumber;

    private String cert;
    /** 可用收款方式 字串 */
    private String availableGatewayStr;

}
