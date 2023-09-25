package com.icchance.q91.service;

import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.TransactionDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.OrderRecord;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;

import java.util.List;

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
     * @return java.util.List<com.icchance.q91.entity.vo.PendingOrderVO>
     * @author 6687353
     * @since 2023/8/18 16:55:29
     */
    List<PendingOrderVO> getPendingOrderList(BaseDTO baseDTO);

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.entity.vo.PendingOrderVO
     * @author 6687353
     * @since 2023/8/18 16:55:35
     */
    PendingOrderVO getPendingOrderDetail(TransactionDTO transactionDTO);

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    void cancelPendingOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 11:25:58
     */
    void checkPendingOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 核實掛單並打幣
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 11:49:56
     */
    void verifyPendingOrder(TransactionDTO transactionDTO);

    void verifyPendingOrder(Integer userId, Integer orderId);

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.vo.OrderVO>
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    List<OrderVO> getOrderList(BaseDTO baseDTO);

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.entity.vo.OrderVO
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    OrderVO getOrderDetail(TransactionDTO transactionDTO);

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 14:17:35
     */
    void cancelOrder(TransactionDTO transactionDTO);

    void cancelOrder(Integer userId, Integer orderId);

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 14:23:42
     */
    void appealOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.model.OrderRecord>
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
    List<OrderRecord> getRecord(BaseDTO baseDTO);

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.model.Gateway>
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    List<Gateway> getGatewayList(BaseDTO baseDTO);

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param gatewayDTO GatewayDTO
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    void createGateway(GatewayDTO gatewayDTO);

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    void deleteGateway(TransactionDTO transactionDTO);

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 15:48:13
     */
    void verifyOrder(TransactionDTO transactionDTO);

    /**
     * <p>
     * 手動打款
     * </p>
     * @param transactionDTO  TransactionDTO
     * @author 6687353
     * @since 2023/9/25 11:13:24
     */
    void manualPay(TransactionDTO transactionDTO);

}
