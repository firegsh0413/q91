package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.TransactionDTO;

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
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:55:29
     */
    Result getPendingOrderList(BaseDTO baseDTO);

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:55:35
     */
    Result getPendingOrderDetail(TransactionDTO transactionDTO);

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    Result cancelPendingOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:25:58
     */
    Result checkPendingOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 核實掛單並打幣
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:49:56
     */
    Result verifyPendingOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    Result getOrderList(BaseDTO baseDTO);

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    Result getOrderDetail(TransactionDTO transactionDTO);

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:17:35
     */
    Result cancelOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:23:42
     */
    Result appealOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
    Result getRecord(BaseDTO baseDTO);

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    Result getGatewayList(BaseDTO baseDTO);

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param gatewayDTO GatewayDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    Result createGateway(GatewayDTO gatewayDTO);

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    Result deleteGateway(TransactionDTO transactionDTO);

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:48:13
     */
    Result verifyOrder(TransactionDTO transactionDTO);

}
