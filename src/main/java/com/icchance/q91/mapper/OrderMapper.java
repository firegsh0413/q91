package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 * 訂單mapper
 * </p>
 * @author 6687353
 * @since 2023/8/24 18:30:20
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderVO> getOrderList(@Param("userId") Integer userId);

    OrderVO getOrderDetail(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

}
