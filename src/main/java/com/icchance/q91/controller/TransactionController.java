package com.icchance.q91.controller;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 交易控制層
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
     * @param userToken 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 10:12:27
     */
    @GetMapping("/pendingOrder")
    public Result getPendingOrderList(@RequestParam String userToken) {
        return transactionService.getPendingOrderList(userToken);
    }

    @GetMapping("/pendingOrder/detail")
    public Result getPendingOrderDetail(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.getPendingOrderDetail(userToken, id);
    }

    @PostMapping("/pendingOrder/cancel")
    public Result cancelPendingOrder(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.cancelPendingOrder(userToken, id);
    }

    /**
     * <p>
     * 確認掛單已下單
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param userToken
     * @param id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:27:31
     */
    @PostMapping("/pendingOrder/check")
    public Result checkPendingOrder(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.checkPendingOrder(userToken, id);
    }

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param userToken
     * @param id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:40:59
     */
    @PostMapping("/pendingOrder/verify")
    public Result verifyPendingOrder(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.verifyPendingOrder(userToken, id);
    }


    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param userToken
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    @GetMapping("/order")
    public Result getOrderList(@RequestParam String userToken) {
        return transactionService.getOrderList(userToken);
    }

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param userToken
     * @param id 訂單id
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    @GetMapping("/order/detail")
    public Result getOrderDetail(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.getOrderDetail(userToken, id);
    }

    @PostMapping("/order/cancel")
    public Result cancelOrder(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.cancelOrder(userToken, id);
    }

    @PostMapping("/order/appeal")
    public Result appealOrder(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.appealOrder(userToken, id);
    }

    @GetMapping("/record")
    public Result getRecord(@RequestParam String userToken) {
        return transactionService.getRecord(userToken);
    }

    @GetMapping("/gateway")
    public Result getGatewayList(@RequestParam String userToken) {
        return transactionService.getGatewayList(userToken);
    }

    @PostMapping("/gateway/add")
    public Result addGateway(@RequestParam String userToken, @RequestParam Integer type, @RequestParam String name, @RequestParam String gatewayName,
                             @RequestParam String gatewayReceiptCode, @RequestParam String gatewayAccount) {
        return transactionService.createGateway(userToken, type, name, gatewayName, gatewayReceiptCode, gatewayAccount);
    }

    @DeleteMapping("/gateway/delete")
    public Result deleteGateway(@RequestParam String userToken, @RequestParam Integer id) {
        return transactionService.deleteGateway(userToken, id);
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param userToken
     * @param id
     * @param cert
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:54:50
     */
    @PostMapping("/order/verify")
    public Result verifyOrder(@RequestParam String userToken, @RequestParam Integer id, @RequestParam String cert) {
        return transactionService.verifyOrder(userToken, id, cert);
    }
}
