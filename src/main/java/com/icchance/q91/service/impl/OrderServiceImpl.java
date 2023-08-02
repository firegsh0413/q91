package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.mapper.OrderMapper;
import com.icchance.q91.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public List<OrderVO> getOrderList(Integer userId) {
        return baseMapper.getOrderList(userId);
    }
}
