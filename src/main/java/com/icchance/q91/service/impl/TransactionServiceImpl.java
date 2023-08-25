package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeTransactionDB;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.*;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.entity.vo.UserBalanceVO;
import com.icchance.q91.service.*;
import com.icchance.q91.util.JwtUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final FakeTransactionDB fakeTransactionDB;
    private final JwtUtil jwtUtil;
    public TransactionServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService,
                                  OrderService orderService, OrderRecordService orderRecordService, UserBalanceService userBalanceService,
                                  FakeTransactionDB fakeTransactionDB, JwtUtil jwtUtil) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
        this.userBalanceService = userBalanceService;
        this.fakeTransactionDB = fakeTransactionDB;
        this.jwtUtil = jwtUtil;
    }

    /**
     * <p>
     * 取得會員掛單訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:58:29
     */
    @Override
    public Result getPendingOrderList(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                //.repData(fakeTransactionDB.getPendingOrderList())
                .repData(pendingOrderService.getList(userId))
                .build();
    }

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 16:58:42
     */
    @Override
    public Result getPendingOrderDetail(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                //.repData(fakeTransactionDB.getPendingOrderDetail())
                .repData(pendingOrderService.getDetail(userId, orderId))
                .build();
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    @Override
    public Result cancelPendingOrder(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, orderId);
        BigDecimal amount = pendingOrderVO.getAmount();
        pendingOrderService.cancel(userId, orderId);
        // 取消掛單 賣方錢包額度要回歸 賣單餘額->可售數量
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(amount));
        sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().add(amount));
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
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:25:58
     */
    @Override
    public Result checkPendingOrder(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, orderId);
        pendingOrderService.check(userId, orderId);
        // 掛單有人下訂 用戶錢包額度 賣單餘額->交易中
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(pendingOrderVO.getAmount()));
        sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().add(pendingOrderVO.getAmount()));
        userBalanceService.updateEntity(sellerBalance);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 核實掛單並打幣
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:49:56
     */
    @Override
    public Result verifyPendingOrder(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, orderId);
        BigDecimal amount = pendingOrderVO.getAmount();
        // TODO 判斷掛單狀態為買家已付款
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
        pendingOrderService.verify(userId, orderId);
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
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 13:10:09
     */
    @Override
    public Result getOrderList(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                //.repData(fakeTransactionDB.getOrderList())
                .repData(orderService.getList(userId))
                .build();
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
    @Override
    public Result getOrderDetail(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                //.repData(fakeTransactionDB.getOrderDetail())
                .repData(orderService.getDetail(userId, orderId))
                .build();
    }

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 11:03:16
     */
    @Override
    public Result cancelOrder(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        OrderVO orderVO = orderService.getDetail(userId, orderId);
        orderService.cancel(userId, orderId);
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
     * @param token 令牌
     * @param orderId 訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 14:23:42
     */
    @Override
    public Result appealOrder(String token, Integer orderId) {
        Integer userId = jwtUtil.parseUserId(token);
        orderService.appeal(userId, orderId);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
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
    @Override
    public Result getRecord(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                //.repData(fakeTransactionDB.getOrderRecordList())
                .repData(orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, userId)))
                .build();
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     *
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    @Override
    public Result getGatewayList(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        List<Gateway> gatewayList = gatewayService.getGatewayList(userId);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(gatewayList).build();
        //return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(fakeTransactionDB.getGatewayList()).build();
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     *
     * @param token          令牌
     * @param type               收付款方式(1.銀行卡 2.微信 3.支付寶)
     * @param name               綁定名字
     * @param gatewayName        綁定名稱
     * @param gatewayReceiptCode 收付款號碼
     * @param gatewayAccount     帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    @Override
    public Result createGateway(String token, Integer type, String name, String gatewayName, String gatewayReceiptCode, String gatewayAccount) {
        Integer userId = jwtUtil.parseUserId(token);
        GatewayDTO gatewayDTO = GatewayDTO.builder()
                .userId(userId)
                .type(type)
                .name(name)
                .gatewayName(gatewayName)
                .gatewayReceiptCode(gatewayReceiptCode)
                .gatewayAccount(gatewayAccount)
                .build();
        gatewayService.createGateway(gatewayDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param token 令牌
     * @param gatewayId        收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    @Override
    public Result deleteGateway(String token, Integer gatewayId) {
        Integer userId = jwtUtil.parseUserId(token);
        gatewayService.deleteGateway(userId, gatewayId);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
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
     * @since 2023/8/22 15:48:13
     */
    @Override
    public Result verifyOrder(String token, Integer orderId, String cert) {
        Integer userId = jwtUtil.parseUserId(token);
        OrderVO orderVO = orderService.getDetail(userId, orderId);
        OrderDTO orderDTO = OrderDTO.builder()
                .id(orderId)
                .cert(cert)
                .build();
        orderService.update(orderDTO);
        // 訂單狀態更新
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .id(orderVO.getPendingOrderId())
                .status(OrderConstant.PendingOrderStatusEnum.ALREADY_PAY.code)
                .cert(cert)
                .build();
        pendingOrderService.update(pendingOrderDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

}
