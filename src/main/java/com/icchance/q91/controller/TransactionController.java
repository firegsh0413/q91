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

    @GetMapping("/pendingOrder")
    public Result getPendingOrder(@RequestParam String userToken) {
        return transactionService.getPendingOrder(userToken);
    }

    @GetMapping("/order")
    public Result getOrder(@RequestParam String userToken) {
        return transactionService.getOrder(userToken);
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
}
