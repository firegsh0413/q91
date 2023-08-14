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

    @GetMapping("/pendingOrderDetail")
    public Result getPendingOrderDetail(@RequestParam String userToken) {
        return null;
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
