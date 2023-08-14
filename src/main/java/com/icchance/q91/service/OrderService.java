package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;

import java.util.List;

public interface OrderService extends IService<Order> {

    List<OrderVO> getOrderList(Integer userId);

    OrderVO getOrderDetail(Integer userId, Integer orderId);

    void createOrder(OrderDTO orderDTO);

    int uploadCert(Integer userId, Integer orderId, String cert);

}
