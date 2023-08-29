package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 收付款方式entity
 * </p>
 * @author 6687353
 * @since 2023/7/28 17:24:41
 */
@Data
@TableName("GATEWAY")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Gateway {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "USER_ID")
    private Integer userId;
    @TableField(value = "TYPE")
    private Integer type;
    @TableField(value = "NAME")
    private String name;
    @TableField(value = "GATEWAY_NAME")
    private String gatewayName;
    @TableField(value = "GATEWAY_RECEIPT_CODE")
    private String gatewayReceiptCode;
    @TableField(value = "GATEWAY_ACCOUNT")
    private String gatewayAccount;
}
