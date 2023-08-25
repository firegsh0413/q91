package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;

import java.util.List;

/**
 * <p>
 * 掛單服務類
 * </p>
 * @author 6687353
 * @since 2023/8/18 16:59:18
 */
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
    List<PendingOrderVO> getList(Integer userId);

    /**
     * <p>
     * 取得掛單詳細資訊
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return com.icchance.q91.entity.vo.PendingOrderVO
     * @author 6687353
     * @since 2023/8/25 11:34:06
     */
    PendingOrderVO getDetail(Integer userId, Integer orderId);

    /**
     * <p>
     * 取得市場買賣訊息（他人的掛單列表）
     * </p>
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/7 13:16:00
     */
    List<MarketVO> getMarketList(MarketDTO marketDTO);

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
    MarketVO getMarketDetail(Integer userId, Integer orderId);

    int uploadCert(Integer userId, Integer orderId, String cert);

    /**
     * <p>
     * 更新掛單
     * </p>
     * @param pendingOrderDTO PendingOrderDTO
     * @return int
     * @author 6687353
     * @since 2023/8/24 10:37:51
     */
    int update(PendingOrderDTO pendingOrderDTO);

    /**
     * <p>
     * 建立掛單
     * </p>
     * @param pendingOrderDTO  PendingOrderDTO
     * @return java.lang.String
     * @author 6687353
     * @since 2023/8/22 16:46:04
     */
    String create(PendingOrderDTO pendingOrderDTO);

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
    int cancel(Integer userId, Integer orderId);

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
    int check(Integer userId, Integer orderId);

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
    int verify(Integer userId, Integer orderId);

}
