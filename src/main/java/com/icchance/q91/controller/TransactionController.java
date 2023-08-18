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

    @UserLoginToken
    @PostMapping("/pendingOrder/cancel")
    public Result cancelPendingOrder(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.cancelPendingOrder(token, id);
    }

    /**
     * <p>
     * 確認掛單已下單
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param token
     * @param id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:27:31
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/check")
    public Result checkPendingOrder(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.checkPendingOrder(token, id);
    }

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param token
     * @param id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:40:59
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/verify")
    public Result verifyPendingOrder(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.verifyPendingOrder(token, id);
    }


    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param token
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
     * @param token
     * @param id 訂單id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    @UserLoginToken
    @GetMapping("/order/detail")
    public Result getOrderDetail(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.getOrderDetail(token, id);
    }

    @UserLoginToken
    @PostMapping("/order/cancel")
    public Result cancelOrder(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.cancelOrder(token, id);
    }

    @UserLoginToken
    @PostMapping("/order/appeal")
    public Result appealOrder(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.appealOrder(token, id);
    }

    @UserLoginToken
    @GetMapping("/record")
    public Result getRecord(@RequestParam String token) {
        return transactionService.getRecord(token);
    }

    @UserLoginToken
    @GetMapping("/gateway")
    public Result getGatewayList(@RequestParam String token) {
        return transactionService.getGatewayList(token);
    }

    @UserLoginToken
    @PostMapping("/gateway/add")
    public Result addGateway(@RequestParam String token, @RequestParam Integer type, @RequestParam String name, @RequestParam String gatewayName,
                             @RequestParam String gatewayReceiptCode, @RequestParam String gatewayAccount) {
        return transactionService.createGateway(token, type, name, gatewayName, gatewayReceiptCode, gatewayAccount);
    }

    @UserLoginToken
    @DeleteMapping("/gateway/delete")
    public Result deleteGateway(@RequestParam String token, @RequestParam Integer id) {
        return transactionService.deleteGateway(token, id);
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param token
     * @param id
     * @param cert
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:54:50
     */
    @UserLoginToken
    @PostMapping("/order/verify")
    public Result verifyOrder(@RequestParam String token, @RequestParam Integer id, @RequestParam String cert) {
        return transactionService.verifyOrder(token, id, cert);
    }
}
