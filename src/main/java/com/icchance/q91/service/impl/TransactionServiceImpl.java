package com.icchance.q91.service.impl;

import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeTransactionDB;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.service.*;
import com.icchance.q91.util.JwtUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

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
    private final FakeTransactionDB fakeTransactionDB;
    private final JwtUtil jwtUtil;
    public TransactionServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService, OrderService orderService,
                                  OrderRecordService orderRecordService, FakeTransactionDB fakeTransactionDB, JwtUtil jwtUtil) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_NOT_EXIST.code).repMsg(ResultCode.ACCOUNT_NOT_EXIST.msg).build();
        }*/
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(pendingOrderService.getPendingOrderList(user.getId()))
                .build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(fakeTransactionDB.getPendingOrderList())
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
        //pendingOrderService.getPendingOrder
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(fakeTransactionDB.getPendingOrderDetail())
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
        //pendingOrderService.cancelPendingOrder
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 確認掛單已下單
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
        //pendingOrderService.checkPendingOrder
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
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
     * @since 2023/8/22 11:49:56
     */
    @Override
    public Result verifyPendingOrder(String token, Integer orderId) {
        //pendingOrderService.verifyPendingOrder
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_NOT_EXIST.code).repMsg(ResultCode.ACCOUNT_NOT_EXIST.msg).build();
        }*/
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(orderService.getOrderList(user.getId()))
                .build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(fakeTransactionDB.getOrderList())
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(orderService.getOrderDetail(user.getId(), id)).build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(fakeTransactionDB.getOrderDetail()).build();
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
        //Integer userId = jwtUtil.parseUserId(token);
        //orderService.cancelOrder(userId, orderId);
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
        //orderService
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_NOT_EXIST.code).repMsg(ResultCode.ACCOUNT_NOT_EXIST.msg).build();
        }*/
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, user.getId())))
                .build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code)
                .repMsg(ResultCode.SUCCESS.msg)
                .repData(fakeTransactionDB.getOrderRecordList())
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        List<Gateway> gatewayList = gatewayService.getGatewayList(user.getAccount());*/

        /*List<Gateway> gatewayList = gatewayService.getGatewayList(0);
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(gatewayList).build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(fakeTransactionDB.getGatewayList()).build();
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        GatewayDTO gatewayDTO = GatewayDTO.builder()
                //.userId(user.getId())
                .type(type)
                .name(name)
                .gatewayName(gatewayName)
                .gatewayReceiptCode(gatewayReceiptCode)
                .gatewayAccount(gatewayAccount)
                .build();
        //gatewayService.createGateway(gatewayDTO);
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        //gatewayService.deleteGateway(user.getId(), id);
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
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        //orderService.uploadCert(user.getId(), id, cert);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

}
