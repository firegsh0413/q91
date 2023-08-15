package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.mapper.OrderMapper;
import com.icchance.q91.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public List<OrderVO> getOrderList(Integer userId) {
        return baseMapper.getOrderList(userId);
    }

    @Override
    public OrderVO getOrderDetail(Integer userId, Integer orderId) {
        return baseMapper.getOrderDetail(userId, orderId);
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = Order.builder().userId(orderDTO.getUserId())
                .status(orderDTO.getStatus())
                .createTime(LocalDateTime.now())
                .sellerId(orderDTO.getSellerId())
                .amount(orderDTO.getAmount())
                .orderNumber(generateOrderNumber(LocalDateTime.now()))
                .build();
        baseMapper.insert(order);
    }

    @Override
    public int uploadCert(Integer userId, Integer orderId, String cert) {
        Order order = Order.builder()
                .id(orderId)
                .status(OrderConstant.OrderStatusEnum.UNCHECK.getCode())
                .updateTime(LocalDateTime.now())
                .cert(cert)
                .build();
        return baseMapper.updateById(order);
    }

    private static String generateOrderNumber(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "go" + localDateTime.format(format);
    }
}
