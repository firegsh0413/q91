package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 訂單可用收款方式 關聯 entity
 * </p>
 * @author 6687353
 * @since 2023/8/24 16:45:26
 */
@Data
@TableName("order_available_gateway")
@Builder
public class OrderAvailableGateway implements Serializable {

    private static final long serialVersionUID = -8265790103201939082L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer gatewayId;
}
