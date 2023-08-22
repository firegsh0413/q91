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
     * @author 6687353
     * @since 2023/8/22 14:16:44
     */
    @Override
    public void create(OrderDTO orderDTO) {
        Order order = Order.builder().userId(orderDTO.getUserId())
                .status(orderDTO.getStatus())
                .createTime(LocalDateTime.now())
                .sellerId(orderDTO.getSellerId())
                .amount(orderDTO.getAmount())
                .orderNumber(generateOrderNumber(LocalDateTime.now()))
                .build();
        baseMapper.insert(order);
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
                .status(OrderConstant.OrderStatusEnum.UNCHECK.getCode())
                .updateTime(LocalDateTime.now())
                .cert(cert)
                .build();
        return baseMapper.updateById(order);
    }

    /**
     * <p>
     * 生成訂單編號
     * </p>
     * @param localDateTime
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/22 15:57:24
     */
    private static String generateOrderNumber(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "go" + localDateTime.format(format);
    }
}
