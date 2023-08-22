package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.OrderDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 市場服務類 介面
 * </p>
 * @author 6687353
 * @since 2023/8/22 16:03:30
 */
public interface MarketService {

    /**
     * <p>
     * 取得市場買賣訊息列表
     * </p>
     * @param token 使用者令牌
     * @param min 最小幣數量
     * @param max 最大幣數量
     * @param gatewayType 收款方式
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    Result getPendingOrderList(String token, BigDecimal min, BigDecimal max, List<Integer> gatewayType);

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param token 令牌
     * @param availableGateway 可用收款方式清單
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:10:40
     */
    Result checkGateway(String token, Set<Integer> availableGateway);

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param token 令牌
     * @param orderId  訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:37
     */
    Result getPendingOrder(String token, Integer orderId);

    /**
     * <p>
     * 購買
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @param amount 數量
     * @param type 付款方式
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:12:14
     */
    Result buy(String token, Integer orderId, BigDecimal amount, Integer type);

    /**
     * <p>
     * 出售
     * </p>
     * @param token 令牌
     * @param amount 數量
     * @param availableGateway 可用收款方式
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    Result sell(String token, BigDecimal amount, List<Integer> availableGateway);
}
