package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.OrderAvailableGateway;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.mapper.PendingOrderMapper;
import com.icchance.q91.service.GatewayService;
import com.icchance.q91.service.OrderAvailableGatewayService;
import com.icchance.q91.service.PendingOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 掛單服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/24 16:26:46
 */
@Service
public class PendingOrderServiceImpl extends ServiceImpl<PendingOrderMapper, PendingOrder> implements PendingOrderService {

    private final OrderAvailableGatewayService orderAvailableGatewayService;
    private final GatewayService gatewayService;
    public PendingOrderServiceImpl(OrderAvailableGatewayService orderAvailableGatewayService, GatewayService gatewayService) {
        this.orderAvailableGatewayService = orderAvailableGatewayService;
        this.gatewayService = gatewayService;
    }

    /**
     * <p>
     * 取得我的掛單
     * </p>
     * @param userId 用戶ID
     * @return java.util.List<com.icchance.q91.entity.vo.PendingOrderVO>
     * @author 6687353
     * @since 2023/8/8 18:29:42
     */
    @Override
    public List<PendingOrderVO> getList(Integer userId) {
        return baseMapper.getPendingOrderList(userId);
    }

    /**
     * <p>
     * 取得掛單詳細資訊
     * </p>
     * @param userId  用戶uid
     * @param orderId 訂單uid
     * @return com.icchance.q91.entity.vo.PendingOrderVO
     * @author 6687353
     * @since 2023/8/25 11:34:06
     */
    @Override
    public PendingOrderVO getDetail(Integer userId, Integer orderId) {
        return baseMapper.getDetail(userId, orderId);
    }

    /**
     * <p>
     * 取得市場買賣訊息（他人的掛單列表）
     * </p>
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/7 13:16:00
     */
    @Override
    public List<MarketVO> getMarketList(MarketDTO marketDTO) {
        return baseMapper.getMarketList(marketDTO);
    }

    /**
     * <p>
     * 取得市場上指定掛單資訊
     * </p>
     * @param userId 用戶UID
     * @param orderId 掛單UID
     * @return com.icchance.q91.entity.vo.MarketVO
     * @author 6687353
     * @since 2023/8/8 18:29:34
     */
    @Override
    public MarketVO getMarketDetail(Integer userId, Integer orderId) {
        return baseMapper.getMarketDetail(userId, orderId);
    }

    @Override
    public int uploadCert(Integer userId, Integer orderId, String cert) {
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(orderId)
                .cert(cert)
                .updateTime(LocalDateTime.now())
                .build();
        return baseMapper.updateById(pendingOrder);
    }

    /**
     * <p>
     * 更新掛單
     * </p>
     * @param pendingOrderDTO PendingOrderDTO
     * @return int
     * @author 6687353
     * @since 2023/8/24 10:37:51
     */
    @Override
    public int update(PendingOrderDTO pendingOrderDTO) {
        PendingOrder pendingOrder = new PendingOrder();
        BeanUtils.copyProperties(pendingOrderDTO, pendingOrder);
        pendingOrder.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(pendingOrder);
    }

    /**
     * <p>
     * 建立掛單
     * </p>
     * @param pendingOrderDTO  PendingOrderDTO
     * @return com.icchance.q91.entity.model.PendingOrder
     * @author 6687353
     * @since 2023/8/22 16:46:04
     */
    @Override
    public PendingOrder create(PendingOrderDTO pendingOrderDTO) {
        String orderNumber = generateOrderNumber(LocalDateTime.now());
        PendingOrder pendingOrder = new PendingOrder();
        BeanUtils.copyProperties(pendingOrderDTO, pendingOrder);
        pendingOrder.setStatus(OrderConstant.PendingOrderStatusEnum.ON_SALE.code);
        pendingOrder.setCreateTime(LocalDateTime.now());
        pendingOrder.setOrderNumber(orderNumber);
        baseMapper.insert(pendingOrder);
        // 可用收款方式 取得收款資訊並存入關聯表
        List<Gateway> gatewayList = gatewayService.getGatewayList(pendingOrderDTO.getUserId());
        List<OrderAvailableGateway> orderAvailableGatewayList = new ArrayList<>();
        for (Gateway gateway : gatewayList) {
            OrderAvailableGateway orderAvailableGateway = OrderAvailableGateway.builder()
                    .orderId(pendingOrder.getId())
                    .gatewayId(gateway.getId())
                    .build();
            orderAvailableGatewayList.add(orderAvailableGateway);
        }
        orderAvailableGatewayService.saveBatch(orderAvailableGatewayList);
        return pendingOrder;
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    @Override
    public int cancel(Integer userId, Integer orderId) {
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(orderId)
                .status(OrderConstant.PendingOrderStatusEnum.CANCEL.code)
                .updateTime(LocalDateTime.now())
                .build();
        return baseMapper.updateById(pendingOrder);
    }

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return int
     * @author 6687353
     * @since 2023/8/22 13:43:08
     */
    @Override
    public int check(Integer userId, Integer orderId) {
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(orderId)
                .status(OrderConstant.PendingOrderStatusEnum.NO_PAY.code)
                .updateTime(LocalDateTime.now())
                // 掛單下單時間為確認訂單的十分後
                .tradeTime(LocalDateTime.now().plusMinutes(10))
                .build();
        return baseMapper.updateById(pendingOrder);
    }

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return int
     * @author 6687353
     * @since 2023/8/22 13:43:43
     */
    @Override
    public int verify(Integer userId, Integer orderId) {
        //PendingOrder pendingOrder = baseMapper.selectOne(Wrappers.<PendingOrder>lambdaQuery().eq(PendingOrder::getUserId, userId).eq(PendingOrder::getId, orderId));
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(orderId)
                .status(OrderConstant.PendingOrderStatusEnum.FINISH.code)
                .updateTime(LocalDateTime.now())
                .build();
        return baseMapper.updateById(pendingOrder);
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
