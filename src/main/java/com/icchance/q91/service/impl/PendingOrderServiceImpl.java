package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.mapper.PendingOrderMapper;
import com.icchance.q91.service.PendingOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<PendingOrderVO> getPendingOrderList(Integer userId) {
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
    public List<MarketVO> getPendingOrderList(MarketDTO marketDTO) {
        return baseMapper.getMarketList(marketDTO);
    }

    /**
     * <p>
     * 取得指定掛單資訊
     * </p>
     * @param userId 用戶UID
     * @param orderId 掛單UID
     * @return com.icchance.q91.entity.vo.MarketVO
     * @author 6687353
     * @since 2023/8/8 18:29:34
     */
    @Override
    public MarketVO getPendingOrder(Integer userId, Integer orderId) {
        return baseMapper.getPendingOrder(userId, orderId);
    }

}
