package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.*;
import com.icchance.q91.entity.model.*;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.service.*;
import com.icchance.q91.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 交易服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/18 16:58:11
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final GatewayService gatewayService;
    private final PendingOrderService pendingOrderService;
    private final OrderService orderService;
    private final OrderRecordService orderRecordService;
    private final UserBalanceService userBalanceService;
    private final JwtUtil jwtUtil;
    public TransactionServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService,
                                  OrderService orderService, OrderRecordService orderRecordService, UserBalanceService userBalanceService,
                                  JwtUtil jwtUtil) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
        this.userBalanceService = userBalanceService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * <p>
     * 取得會員掛單訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:58:29
     */
    @Override
    public Result getPendingOrderList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(pendingOrderService.getList(userId))
                .build();
    }

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:58:42
     */
    @Override
    public Result getPendingOrderDetail(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(pendingOrderService.getDetail(userId, transactionDTO.getId()))
                .build();
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    @Override
    public Result cancelPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(pendingOrderVO)) {
            return Result.builder().repCode(ResultCode.NO_ORDER_EXIST.code).repMsg(ResultCode.NO_ORDER_EXIST.msg).build();
        }
        if (OrderConstant.PendingOrderStatusEnum.FINISH.code.equals(pendingOrderVO.getStatus())) {
            return Result.builder().repCode(ResultCode.ORDER_FINISH.code).repMsg(ResultCode.ORDER_FINISH.msg).build();
        }
        BigDecimal amount = pendingOrderVO.getAmount();
        pendingOrderService.cancel(userId, transactionDTO.getId());
        // 取消掛單 賣方錢包額度要回歸
        // 1.掛單狀態為掛賣中 賣單餘額->可售數量
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        if (OrderConstant.PendingOrderStatusEnum.ON_PENDING.code.equals(pendingOrderVO.getStatus())) {
            sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(amount));
            sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().add(amount));
        }
        // 2.掛單狀態為有人下訂 交易中->可售數量
        else {
            sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().subtract(amount));
            sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().add(amount));
        }
        userBalanceService.updateEntity(sellerBalance);
        // 如果有買方 已下訂訂單取消
        // 錢包額度 交易中額度扣除
        if (Objects.nonNull(pendingOrderVO.getBuyerId())) {
            orderService.cancel(pendingOrderVO.getBuyerId(), pendingOrderVO.getOrderId());
            UserBalance buyerBalance = userBalanceService.getEntity(pendingOrderVO.getBuyerId());
            buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(amount));
            userBalanceService.updateEntity(buyerBalance);
        }
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

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
    @Override
    public Result checkPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, transactionDTO.getId());
        // 掛單狀態為有人下單
        if (Objects.nonNull(pendingOrderVO) && OrderConstant.PendingOrderStatusEnum.ON_ORDER.code.equals(pendingOrderVO.getStatus())) {
            pendingOrderService.check(userId, transactionDTO.getId());
            // 更新訂單狀態
            Order order = Order.builder()
                    .id(pendingOrderVO.getOrderId())
                    .status(OrderConstant.OrderStatusEnum.SELLER_CHECKED.code)
                    .updateTime(LocalDateTime.now())
                    .cutOffTime(LocalDateTime.now().plusMinutes(10)).build();
            orderService.updateById(order);
            // 掛單有人下訂 用戶錢包額度 賣單餘額->交易中
            UserBalance sellerBalance = userBalanceService.getEntity(userId);
            sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(pendingOrderVO.getAmount()));
            sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().add(pendingOrderVO.getAmount()));
            userBalanceService.updateEntity(sellerBalance);
        }
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

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
    @Override
    public Result verifyPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, transactionDTO.getId());
        BigDecimal amount = pendingOrderVO.getAmount();
        // 判斷掛單狀態是否為買家已付款
        if (!OrderConstant.PendingOrderStatusEnum.ALREADY_PAY.code.equals(pendingOrderVO.getStatus())) {
            return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
        }
/*        if (LocalDateTime.now().isAfter(pendingOrderVO.getCutOffTime())) {

        }*/
        // 掛單有人下訂 用戶錢包額度 從交易中扣除
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().subtract(amount));
        userBalanceService.updateEntity(sellerBalance);
        // 打幣給買家 交易中->可售數量/錢包餘額
        UserBalance buyerBalance = userBalanceService.getEntity(pendingOrderVO.getBuyerId());
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(amount));
        buyerBalance.setBalance(buyerBalance.getBalance().add(amount));
        buyerBalance.setAvailableAmount(buyerBalance.getAvailableAmount().add(amount));
        userBalanceService.updateEntity(buyerBalance);
        pendingOrderService.verify(userId, transactionDTO.getId());
        // 更新訂單狀態
        OrderDTO orderDTO = OrderDTO.builder()
                .id(pendingOrderVO.getOrderId())
                .status(OrderConstant.OrderStatusEnum.FINISH.code)
                .build();
        orderService.update(orderDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 13:10:09
     */
    @Override
    public Result getOrderList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(orderService.getList(userId))
                .build();
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
    @Override
    public Result getOrderDetail(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(orderService.getDetail(userId, transactionDTO.getId()))
                .build();
    }

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:03:16
     */
    @Override
    public Result cancelOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        orderService.cancel(userId, transactionDTO.getId());
        // 取消訂單 買方錢包額度 交易中移除額度
        UserBalance buyerBalance = userBalanceService.getEntity(userId);
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(orderVO.getAmount()));
        userBalanceService.updateEntity(buyerBalance);
        // 賣方掛單更新
        // 錢包額度 交易中->賣單餘額
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .id(orderVO.getPendingOrderId())
                .status(OrderConstant.PendingOrderStatusEnum.ON_PENDING.code)
                // 買方資訊清空
                .orderId(null)
                .buyerId(null)
                .buyerGatewayId(null)
                .tradeTime(null)
                //.cutOffTime(null)
                .build();
        pendingOrderService.update(pendingOrderDTO);
        UserBalance sellBalance = userBalanceService.getEntity(orderVO.getSellerId());
        sellBalance.setTradingAmount(sellBalance.getTradingAmount().subtract(orderVO.getAmount()));
        sellBalance.setPendingBalance(sellBalance.getPendingBalance().add(orderVO.getAmount()));
        userBalanceService.updateEntity(sellBalance);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 申訴訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:23:42
     */
    @Override
    public Result appealOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        orderService.appeal(userId, transactionDTO.getId());
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
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
    @Override
    public Result getRecord(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, userId)))
                .build();
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     *
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    @Override
    public Result getGatewayList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        List<Gateway> gatewayList = gatewayService.getGatewayList(userId);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(gatewayList).build();
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     *
     * @param gatewayDTO GatewayDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    @Override
    public Result createGateway(GatewayDTO gatewayDTO) {
        Integer userId = jwtUtil.parseUserId(gatewayDTO.getToken());
        gatewayDTO.setUserId(userId);
        gatewayService.createGateway(gatewayDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    @Override
    public Result deleteGateway(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        gatewayService.deleteGateway(userId, transactionDTO.getId());
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 15:48:13
     */
    @Override
    public Result verifyOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(orderVO)) {
            return Result.builder().repCode(ResultCode.NO_ORDER_EXIST.code).repMsg(ResultCode.NO_ORDER_EXIST.msg).build();
        }
        // TODO 判斷超過截止時間 取消訂單
        if (LocalDateTime.now().isAfter(orderVO.getCutOffTime())) {
            this.cancelOrder(transactionDTO);
        }
        //OrderDTO orderDTO = OrderDTO.builder().id(transactionDTO.getId()).cert(transactionDTO.getCert()).build();
        //orderService.update(orderDTO);
        orderService.uploadCert(userId, orderVO.getId(), transactionDTO.getCert());
        // 訂單狀態更新
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .id(orderVO.getPendingOrderId())
                .status(OrderConstant.PendingOrderStatusEnum.ALREADY_PAY.code)
                //.cutOffTime(LocalDateTime.now().plusMinutes(10))
                .cert(transactionDTO.getCert())
                .build();
        pendingOrderService.update(pendingOrderDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

}
