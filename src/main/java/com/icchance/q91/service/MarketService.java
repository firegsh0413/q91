package com.icchance.q91.service;

import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.MarketInfoDTO;
import com.icchance.q91.entity.vo.CheckGatewayVO;
import com.icchance.q91.entity.vo.MarketVO;

import java.util.List;

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
    List<MarketVO> getPendingOrderList(MarketDTO marketDTO);

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.entity.vo.CheckGatewayVO
     * @author 6687353
     * @since 2023/8/22 16:10:40
     */
    CheckGatewayVO checkGateway(MarketInfoDTO marketInfoDTO);

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param marketDTO MarketDTO
     * @return com.icchance.q91.entity.vo.MarketVO
     * @author 6687353
     * @since 2023/8/22 16:11:37
     */
    MarketVO getPendingOrder(MarketDTO marketDTO);

    /**
     * <p>
     * 購買
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @author 6687353
     * @since 2023/8/22 16:12:14
     */
    void buy(MarketInfoDTO marketInfoDTO);

    /**
     * <p>
     * 出售
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    void sell(MarketInfoDTO marketInfoDTO);
}
