package com.icchance.q91.entity.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * <p>  
 * 訂單資料entity
 * </p>  
 * @author 6687353 
 * @since 2023/08/01 15:23:24 
 */
@Data
@TableName("orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID =  5571934468216394059L;

   	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer userId;

	private Integer status;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private LocalDateTime tradeTime;

	private LocalDateTime cutOffTime;

	private Integer sellerId;

	private BigDecimal amount;

	private String orderNumber;

	private String cert;

	private Integer buyerGatewayId;

	private Integer sellerGatewayId;

	private Integer pendingOrderId;
}
