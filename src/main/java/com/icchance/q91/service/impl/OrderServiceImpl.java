package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.mapper.OrderMapper;
import com.icchance.q91.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 訂單服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/22 14:03:09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param userId 用戶uid
     * @return java.util.List<com.icchance.q91.entity.vo.OrderVO>
     * @author 6687353
     * @since 2023/8/22 14:02:29
     */
    @Override
    public List<OrderVO> getList(Integer userId) {
        return baseMapper.getOrderList(userId);
    }

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return com.icchance.q91.entity.vo.OrderVO
     * @author 6687353
     * @since 2023/8/22 14:11:30
     */
    @Override
    public OrderVO getDetail(Integer userId, Integer orderId) {
        return baseMapper.getOrderDetail(userId, orderId);
    }

    /**
     * <p>
     * 建立訂單
     * </p>
     * @param orderDTO OrderDTO
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/22 14:16:44
     */
    @Override
    public Order create(OrderDTO orderDTO) {
        String orderNumber = generateOrderNumber(LocalDateTime.now());
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setStatus(OrderConstant.OrderStatusEnum.ON_CHECK.code);
        order.setOrderNumber(orderNumber);
        order.setCreateTime(LocalDateTime.now());
        baseMapper.insert(order);
        return order;
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @param cert 憑證base64
     * @return int
     * @author 6687353
     * @since 2023/8/22 15:56:31
     */
    @Override
    public int uploadCert(Integer userId, Integer orderId, String cert) {
        Order order = Order.builder()
                .id(orderId)
                .status(OrderConstant.OrderStatusEnum.ON_TRANSACTION.code)
                .updateTime(LocalDateTime.now())
                .cert(cert)
                .build();
        return baseMapper.updateById(order);
    }

    /**
     * <p>
     * 更新訂單
     * </p>
     * @param orderDTO OrderDTO
     * @return int
     * @author 6687353
     * @since 2023/8/25 18:54:13
     */
    @Override
    public int update(OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(order);
    }

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param userId  用戶uid
     * @param orderId 訂單uid
     * @return int
     * @author 6687353
     * @since 2023/8/25 17:27:18
     */
    @Override
    public int cancel(Integer userId, Integer orderId) {
        Order order = Order.builder()
                .id(orderId)
                .status(OrderConstant.OrderStatusEnum.CANCEL.code)
                .updateTime(LocalDateTime.now())
                .build();
        return baseMapper.updateById(order);
    }

    /**
     * <p>
     * 訂單申訴
     * </p>
     * @param userId  用戶uid
     * @param orderId 訂單uid
     * @return int
     * @author 6687353
     * @since 2023/8/25 18:32:30
     */
    @Override
    public int appeal(Integer userId, Integer orderId) {
        Order order = Order.builder()
                .id(orderId)
                .status(OrderConstant.OrderStatusEnum.APPEAL.code)
                .updateTime(LocalDateTime.now())
                .build();
        return baseMapper.updateById(order);
    }

    /**
     * <p>
     * 生成訂單編號
     * </p>
     * @param localDateTime 日期
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/22 15:57:24
     */
    private static String generateOrderNumber(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "go" + localDateTime.format(format);
    }
}
