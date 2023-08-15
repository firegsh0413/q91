package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;

public interface TransactionService {

    Result getPendingOrderList(String userToken);

    Result getPendingOrderDetail(String userToken, Integer id);

    Result cancelPendingOrder(String userToken, Integer id);

    Result checkPendingOrder(String userToken, Integer id);

    Result verifyPendingOrder(String userToken, Integer id);

    Result getOrderList(String userToken);

    Result getOrderDetail(String userToken, Integer id);

    Result cancelOrder(String userToken, Integer id);

    Result appealOrder(String userToken, Integer id);

    Result getRecord(String userToken);

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param userToken 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    Result getGatewayList(String userToken);

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param userToken 令牌
     * @param type 收付款方式(1.銀行卡 2.微信 3.支付寶)
     * @param name 綁定名字
     * @param gatewayName 綁定名稱
     * @param gatewayReceiptCode 收付款號碼
     * @param gatewayAccount 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    Result createGateway(String userToken, Integer type, String name, String gatewayName, String gatewayReceiptCode, String gatewayAccount);

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param userToken 令牌
     * @param id 收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    Result deleteGateway(String userToken, Integer id);

    Result verifyOrder(String userToken, Integer id, String cert);

}
