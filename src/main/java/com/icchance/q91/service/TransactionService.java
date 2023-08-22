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

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    Result cancelPendingOrder(String token, Integer orderId);

    /**
     * <p>
     * 確認掛單已下單
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:25:58
     */
    Result checkPendingOrder(String token, Integer orderId);

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:49:56
     */
    Result verifyPendingOrder(String token, Integer orderId);

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    Result getOrderList(String token);

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    Result getOrderDetail(String token, Integer orderId);

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:17:35
     */
    Result cancelOrder(String token, Integer orderId);

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:23:42
     */
    Result appealOrder(String token, Integer orderId);

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
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
     * @param gatewayId 收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    Result deleteGateway(String token, Integer gatewayId);

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @param cert 憑證base64
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:48:13
     */
    Result verifyOrder(String token, Integer orderId, String cert);

}
