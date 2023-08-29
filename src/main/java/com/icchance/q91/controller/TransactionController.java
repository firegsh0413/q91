package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.TransactionDTO;
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
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 10:12:27
     */
    @UserLoginToken
    @GetMapping("/pendingOrder")
    public Result getPendingOrderList(@RequestBody BaseDTO baseDTO) {
        return transactionService.getPendingOrderList(baseDTO);
    }

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:53:22
     */
    @UserLoginToken
    @GetMapping("/pendingOrder/detail")
    public Result getPendingOrderDetail(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.getPendingOrderDetail(transactionDTO);
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:32:56
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/cancel")
    public Result cancelPendingOrder(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.cancelPendingOrder(transactionDTO);
    }

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:27:31
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/check")
    public Result checkPendingOrder(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.checkPendingOrder(transactionDTO);
    }

    /**
     * <p>
     * 核實掛單
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 17:40:59
     */
    @UserLoginToken
    @PostMapping("/pendingOrder/verify")
    public Result verifyPendingOrder(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.verifyPendingOrder(transactionDTO);
    }

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:44
     */
    @UserLoginToken
    @GetMapping("/order")
    public Result getOrderList(@RequestBody BaseDTO baseDTO) {
        return transactionService.getOrderList(baseDTO);
    }

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    @UserLoginToken
    @GetMapping("/order/detail")
    public Result getOrderDetail(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.getOrderDetail(transactionDTO);
    }

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:17:35
     */
    @UserLoginToken
    @PostMapping("/order/cancel")
    public Result cancelOrder(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.cancelOrder(transactionDTO);
    }

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:22:51
     */
    @UserLoginToken
    @PostMapping("/order/appeal")
    public Result appealOrder(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.appealOrder(transactionDTO);
    }

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
    @UserLoginToken
    @GetMapping("/record")
    public Result getRecord(@RequestBody BaseDTO baseDTO) {
        return transactionService.getRecord(baseDTO);
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:30:29
     */
    @UserLoginToken
    @GetMapping("/gateway")
    public Result getGatewayList(@RequestBody BaseDTO baseDTO) {
        return transactionService.getGatewayList(baseDTO);
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param gatewayDTO GatewayDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:31:24
     */
    @UserLoginToken
    @PostMapping("/gateway/add")
    public Result addGateway(@RequestBody GatewayDTO gatewayDTO) {
        return transactionService.createGateway(gatewayDTO);
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:35:41
     */
    @UserLoginToken
    @DeleteMapping("/gateway/delete")
    public Result deleteGateway(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.deleteGateway(transactionDTO);
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param orderDTO OrderDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:54:50
     */
    @UserLoginToken
    @PostMapping("/order/verify")
    public Result verifyOrder(@RequestBody OrderDTO orderDTO) {
        return transactionService.verifyOrder(orderDTO);
    }
}
