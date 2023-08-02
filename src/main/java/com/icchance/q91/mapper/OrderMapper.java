package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<OrderVO> getOrderList(@Param("userId") Integer userId);

}
