package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.mapper.PendingOrderMapper;
import com.icchance.q91.service.PendingOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PendingOrderServiceImpl extends ServiceImpl<PendingOrderMapper, PendingOrder> implements PendingOrderService {

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
     * 取得市場買賣訊息（他人的掛單列表）
     * </p>
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/7 13:16:00
     */
    @Override
    public List<MarketVO> getList(MarketDTO marketDTO) {
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
    public MarketVO getDetail(Integer userId, Integer orderId) {
        return baseMapper.getPendingOrder(userId, orderId);
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

    @Override
    public int uploadPendingOrder(PendingOrderDTO pendingOrderDTO) {
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
     * @return int
     * @author 6687353
     * @since 2023/8/22 16:19:47
     */
    @Override
    public int createPendingOrder(PendingOrderDTO pendingOrderDTO) {
        PendingOrder pendingOrder = new PendingOrder();
        BeanUtils.copyProperties(pendingOrder, pendingOrder);
        pendingOrder.setCreateTime(LocalDateTime.now());
        return baseMapper.insert(pendingOrder);
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
    public int cancelPendingOrder(Integer userId, Integer orderId) {
        PendingOrder pendingOrder = baseMapper.selectOne(Wrappers.<PendingOrder>lambdaQuery().eq(PendingOrder::getUserId, userId).eq(PendingOrder::getId, orderId));
        pendingOrder.setStatus(OrderConstant.OrderStatusEnum.CANCEL.getCode());
        pendingOrder.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(pendingOrder);
    }

    /**
     * <p>
     * 確認掛單已下單
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return int
     * @author 6687353
     * @since 2023/8/22 13:43:08
     */
    @Override
    public int checkPendingOrder(Integer userId, Integer orderId) {
        PendingOrder pendingOrder = baseMapper.selectOne(Wrappers.<PendingOrder>lambdaQuery().eq(PendingOrder::getUserId, userId).eq(PendingOrder::getId, orderId));
        pendingOrder.setStatus(OrderConstant.OrderStatusEnum.UNCHECK.getCode());
        pendingOrder.setUpdateTime(LocalDateTime.now());
        pendingOrder.setTradeTime(LocalDateTime.now().plusMinutes(10));
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
    public int verifyPendingOrder(Integer userId, Integer orderId) {
        PendingOrder pendingOrder = baseMapper.selectOne(Wrappers.<PendingOrder>lambdaQuery().eq(PendingOrder::getUserId, userId).eq(PendingOrder::getId, orderId));
        pendingOrder.setStatus(OrderConstant.OrderStatusEnum.DONE.getCode());
        pendingOrder.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(pendingOrder);
    }

}
