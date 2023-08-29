package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.MarketInfoDTO;
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
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    Result getPendingOrderList(MarketDTO marketDTO);

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:10:40
     */
    Result checkGateway(MarketInfoDTO marketInfoDTO);

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param marketDTO MarketDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:37
     */
    Result getPendingOrder(MarketDTO marketDTO);

    /**
     * <p>
     * 購買
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:12:14
     */
    Result buy(MarketInfoDTO marketInfoDTO);

    /**
     * <p>
     * 出售
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    Result sell(MarketInfoDTO marketInfoDTO);
}
