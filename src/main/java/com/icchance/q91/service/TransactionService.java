package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;

/**
 * <p>
 * 交易服務類
 * </p>
 * @author 6687353
 * @since 2023/8/18 16:57:54
 */
public interface TransactionService {

    /**
     * <p>
     * 取得會員掛單訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:55:29
     */
    Result getPendingOrderList(String token);

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:55:35
     */
    Result getPendingOrderDetail(String token, Integer orderId);

    Result cancelPendingOrder(String token, Integer id);

    Result checkPendingOrder(String token, Integer id);

    Result verifyPendingOrder(String token, Integer id);

    Result getOrderList(String token);

    Result getOrderDetail(String token, Integer id);

    Result cancelOrder(String token, Integer id);

    Result appealOrder(String token, Integer id);

    Result getRecord(String token);

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    Result getGatewayList(String token);

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param token 令牌
     * @param type 收付款方式(1.銀行卡 2.微信 3.支付寶)
     * @param name 綁定名字
     * @param gatewayName 綁定名稱
     * @param gatewayReceiptCode 收付款號碼
     * @param gatewayAccount 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    Result createGateway(String token, Integer type, String name, String gatewayName, String gatewayReceiptCode, String gatewayAccount);

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param token 令牌
     * @param id 收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    Result deleteGateway(String token, Integer id);

    Result verifyOrder(String token, Integer id, String cert);

}
