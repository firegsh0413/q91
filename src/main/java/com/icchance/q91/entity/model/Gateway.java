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
@TableName("gateway")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Gateway {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer type;

    private String name;

    private String gatewayName;

    private String gatewayReceiptCode;

    private String gatewayAccount;
}
