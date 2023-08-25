package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 交易控制類
 * </p>
 * @author 6687353
 * @since 2023/7/28 18:12:42
 */
@Slf4j
@RequestMapping(TransactionController.PREFIX)
@RestController
public class TransactionController extends BaseController {

    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    static final String PREFIX = "/transaction";

    /**
     * <p>
     * 取得會員掛單訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 10:12:27
     */
    @UserLoginToken
    @GetMapping("/pendingOrder")
    public Result getPendingOrderList(@RequestParam String token) {
        return transactionService.getPendingOrderList(token);
    }

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:53:22
     */
    @UserLoginToken
    @GetMapping("/pendingOrder/detail")
    public Result getPendingOrderDetail(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.getPendingOrderDetail(token, orderId);
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:32:56
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/cancel")
    public Result cancelPendingOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.cancelPendingOrder(token, orderId);
    }

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:27:31
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/check")
    public Result checkPendingOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.checkPendingOrder(token, orderId);
    }

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:40:59
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/verify")
    public Result verifyPendingOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.verifyPendingOrder(token, orderId);
    }

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    @UserLoginToken
    @GetMapping("/order")
    public Result getOrderList(@RequestParam String token) {
        return transactionService.getOrderList(token);
    }

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
    @UserLoginToken
    @GetMapping("/order/detail")
    public Result getOrderDetail(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.getOrderDetail(token, orderId);
    }

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
    @UserLoginToken
    @PostMapping("/order/cancel")
    public Result cancelOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.cancelOrder(token, orderId);
    }

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:22:51
     */
    @UserLoginToken
    @PostMapping("/order/appeal")
    public Result appealOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId) {
        return transactionService.appealOrder(token, orderId);
    }

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
    @UserLoginToken
    @GetMapping("/record")
    public Result getRecord(@RequestParam String token) {
        return transactionService.getRecord(token);
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:30:29
     */
    @UserLoginToken
    @GetMapping("/gateway")
    public Result getGatewayList(@RequestParam String token) {
        return transactionService.getGatewayList(token);
    }

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
     * @since 2023/8/22 15:31:24
     */
    @UserLoginToken
    @PostMapping("/gateway/add")
    public Result addGateway(@RequestParam String token, @RequestParam Integer type, @RequestParam String name, @RequestParam String gatewayName,
                             @RequestParam String gatewayReceiptCode, @RequestParam String gatewayAccount) {
        return transactionService.createGateway(token, type, name, gatewayName, gatewayReceiptCode, gatewayAccount);
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param token 令牌
     * @param gatewayId 收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:35:41
     */
    @UserLoginToken
    @DeleteMapping("/gateway/delete")
    public Result deleteGateway(@RequestParam String token, @RequestParam(value = "id") Integer gatewayId) {
        return transactionService.deleteGateway(token, gatewayId);
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @param cert 憑證base64
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:54:50
     */
    @UserLoginToken
    @PostMapping("/order/verify")
    public Result verifyOrder(@RequestParam String token, @RequestParam(value = "id") Integer orderId, @RequestParam String cert) {
        return transactionService.verifyOrder(token, orderId, cert);
    }
}
