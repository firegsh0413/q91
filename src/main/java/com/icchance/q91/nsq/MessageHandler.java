package com.icchance.q91.nsq;

import com.alibaba.fastjson.JSON;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.entity.dto.TransactionDTO;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * NSQ訊息處理
 * </p>
 * @author 6687353
 * @since 2023/9/20 14:18:23
 */
@Component
@Slf4j
public class MessageHandler {

    private final TransactionService transactionService;

    public MessageHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * <p>
     * 訂單賣家確認後 取得買家必須上傳支付訊息
     * </p>
     * @param data 訊息主體
     * @author 6687353
     * @since 2023/9/20 14:18:49
     */
    public void getCheckOrderConsumer(byte[] data) {
        log.info("MessageHandler.getCheckOrderConsumer, data:{}", data);
        TransactionDTO transactionDTO = JSON.parseObject(new String(data), TransactionDTO.class);
        PendingOrderVO pendingOrderDetail = transactionService.getPendingOrderDetail(transactionDTO);
        // 還在買家未付款則取消訂單
        if (OrderConstant.PendingOrderStatusEnum.PAY_UPLOAD.code.equals(pendingOrderDetail.getStatus())) {
            transactionService.cancelOrder(pendingOrderDetail.getBuyerId(), pendingOrderDetail.getOrderId());
        }
    }

    /**
     * <p>
     * 掛單買家支付後 取得賣家必須打幣訊息
     * </p>
     * @param data 訊息主體
     * @author 6687353
     * @since 2023/9/20 14:19:07
     */
    public void getUploadCertConsumer(byte[] data) {
        log.info("MessageHandler.getUploadCertConsumer, data:{}", data);
        TransactionDTO transactionDTO = JSON.parseObject(new String(data), TransactionDTO.class);
        OrderVO orderDetail = transactionService.getOrderDetail(transactionDTO);
        if (OrderConstant.OrderStatusEnum.ON_TRANSACTION.code.equals(orderDetail.getStatus())) {
            transactionService.verifyPendingOrder(orderDetail.getSellerId(), orderDetail.getPendingOrderId());
        }
    }
}
