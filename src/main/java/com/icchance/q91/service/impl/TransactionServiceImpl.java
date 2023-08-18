package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeTransactionDB;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.OrderRecord;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final GatewayService gatewayService;
    private final PendingOrderService pendingOrderService;
    private final OrderService orderService;
    private final OrderRecordService orderRecordService;

    private final FakeTransactionDB fakeTransactionDB;
    public TransactionServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService, OrderService orderService,
                                  OrderRecordService orderRecordService, FakeTransactionDB fakeTransactionDB) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
        this.fakeTransactionDB = fakeTransactionDB;
    }

    @Override
    public Result getPendingOrderList(String token) {
        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(pendingOrderService.getPendingOrderList(user.getId()))
                .build();*/
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(fakeTransactionDB.getPendingOrderList())
                .build();
    }

    @Override
    public Result getPendingOrderDetail(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(fakeTransactionDB.getPendingOrderDetail())
                .build();
    }

    @Override
    public Result cancelPendingOrder(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result checkPendingOrder(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result verifyPendingOrder(String token, Integer id) {
        return null;
    }

    @Override
    public Result getOrderList(String token) {
        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(orderService.getOrderList(user.getId()))
                .build();*/
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(fakeTransactionDB.getOrderList())
                .build();
    }

    @Override
    public Result getOrderDetail(String token, Integer id) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(orderService.getOrderDetail(user.getId(), id)).build();*/
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeTransactionDB.getOrderDetail()).build();
    }

    @Override
    public Result cancelOrder(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result appealOrder(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result getRecord(String token) {
        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
/*        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, user.getId())))
                .build();*/
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(fakeTransactionDB.getOrderRecordList())
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
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeTransactionDB.getGatewayList()).build();
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
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param token 令牌
     * @param id        收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    @Override
    public Result deleteGateway(String token, Integer id) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        //gatewayService.deleteGateway(user.getId(), id);
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result verifyOrder(String token, Integer id, String cert) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        //orderService.uploadCert(user.getId(), id, cert);
        return Result.builder().resultMap(ResultCode.SUCCESS).build();
    }

}
