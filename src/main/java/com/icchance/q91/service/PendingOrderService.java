package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;

import java.util.List;

public interface PendingOrderService extends IService<PendingOrder> {

    /**
     * <p>
     * 取得我的掛單
     * </p>
     * @param userId 用戶ID
     * @return java.util.List<com.icchance.q91.entity.vo.PendingOrderVO>
     * @author 6687353
     * @since 2023/8/8 18:29:42
     */
    List<PendingOrderVO> getPendingOrderList(Integer userId);

    /**
     * <p>
     * 取得市場買賣訊息（他人的掛單列表）
     * </p>
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/7 13:16:00
     */
    List<MarketVO> getPendingOrderList(MarketDTO marketDTO);

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
    MarketVO getPendingOrder(Integer userId, Integer orderId);
}
