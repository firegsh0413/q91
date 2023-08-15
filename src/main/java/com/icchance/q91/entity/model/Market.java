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
import java.math.BigDecimal;

/**
 * <p>  
 *   
 * </p>  
 * @author 6687353 
 * @since 2023/08/02 17:10:00 
 */
@Data
@TableName("MARKET")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Market implements Serializable {

	private static final long serialVersionUID =  2354129129431744782L;

   	@TableId(value = "ID")
	private Integer id;

	private Integer sellerId;

	private String sellerUsername;

	private String sellerAvatar;

	private BigDecimal amount;

	private String orderNumber;

	private String availableGateway;

}
