package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.error.group.Order;
import com.icchance.q91.common.error.group.OrderVerify;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.TransactionDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.OrderRecord;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @PostMapping("/pendingOrder")
    public Result<List<PendingOrderVO>> getPendingOrderList(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(transactionService.getPendingOrderList(baseDTO)).build();
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
    @PostMapping("/pendingOrder/detail")
    public Result<PendingOrderVO> getPendingOrderDetail(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        return SUCCESS_DATA.repData(transactionService.getPendingOrderDetail(transactionDTO)).build();
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
    public Result<Void> cancelPendingOrder(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.cancelPendingOrder(transactionDTO);
        return SUCCESS;
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
    public Result<Void> checkPendingOrder(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.checkPendingOrder(transactionDTO);
        return SUCCESS;
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
    public Result<Void> verifyPendingOrder(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.verifyPendingOrder(transactionDTO);
        return SUCCESS;
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
    @PostMapping("/order")
    public Result<List<OrderVO>> getOrderList(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(transactionService.getOrderList(baseDTO)).build();
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
    @PostMapping("/order/detail")
    public Result<OrderVO> getOrderDetail(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        return SUCCESS_DATA.repData(transactionService.getOrderDetail(transactionDTO)).build();
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
    public Result<Void> cancelOrder(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.cancelOrder(transactionDTO);
        return SUCCESS;
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
    public Result<Void> appealOrder(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.appealOrder(transactionDTO);
        return SUCCESS;
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
    @PostMapping("/record")
    public Result<List<OrderRecord>> getRecord(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(transactionService.getRecord(baseDTO)).build();
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
    @PostMapping("/gateway")
    public Result<List<Gateway>> getGatewayList(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(transactionService.getGatewayList(baseDTO)).build();
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
    public Result<Void> addGateway(@RequestBody @Valid GatewayDTO gatewayDTO) {
        transactionService.createGateway(gatewayDTO);
        return SUCCESS;
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
    public Result<Void> deleteGateway(@RequestBody @Validated({Order.class}) TransactionDTO transactionDTO) {
        transactionService.deleteGateway(transactionDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/11 11:54:50
     */
    @UserLoginToken
    @PostMapping("/order/verify")
    public Result<Void> verifyOrder(@RequestBody @Validated({OrderVerify.class}) TransactionDTO transactionDTO) {
        transactionService.verifyOrder(transactionDTO);
        return SUCCESS;
    }
}
