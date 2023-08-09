package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface MarketService {

    /**
     * <p>
     * 取得市場買賣訊息列表
     * </p>
     * @param userToken 使用者令牌
     * @param min 最小幣數量
     * @param max 最大幣數量
     * @param gatewayType 收款方式
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    Result getPendingOrderList(String userToken, BigDecimal min, BigDecimal max, List<Integer> gatewayType);

    Result checkGateway(String userToken, Set<Integer> availableGateway);

    Result getPendingOrder(String userToken, Integer orderId);
}
