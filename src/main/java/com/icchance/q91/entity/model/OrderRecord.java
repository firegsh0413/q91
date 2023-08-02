package com.icchance.q91.entity.model;

import java.io.Serializable;
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
 *   
 * </p>  
 * @author 6687353 
 * @since 2023/08/02 14:44:22 
 */
@Data
@TableName("order_record")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecord implements Serializable {

	private static final long serialVersionUID =  1261584036056598776L;

   	@TableId(value = "ID")
	private Integer id;

	private Integer status;

	private BigDecimal amount;

	private String orderNumber;

	private LocalDateTime createTime;

	private Integer userId;
}
