package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
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
    public TransactionServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService, OrderService orderService,
                                  OrderRecordService orderRecordService) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
    }

    @Override
    public Result getPendingOrder(String userToken) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        //List<PendingOrder> pendingOrder = pendingOrderService.getPendingOrder(user.getAccount());
        //List<Gateway> gatewayList = gatewayService.getGatewayList(user.getAccount());

        //List<PendingOrderVO> result = new ArrayList<>(pendingOrder.size());
/*        pendingOrder.forEach(o -> {
            PendingOrderVO vo = new PendingOrderVO();
            BeanUtils.copyProperties(o, vo);
            vo.setAvailableTradeInfo(gatewayList);
            vo.setCreateTime(o.getCreateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
            vo.setUpdateTime(o.getUpdateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
            vo.setTradeTime(o.getTradeTime().toInstant(ZoneOffset.UTC).toEpochMilli());
            result.add(vo);
        });*/
        List<PendingOrderVO> pendingOrderList = pendingOrderService.getPendingOrderList(user.getId());
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(pendingOrderList).build();
    }

    @Override
    public Result getOrder(String userToken) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        List<OrderVO> orderList = orderService.getOrderList(user.getId());
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(orderList).build();
    }

    @Override
    public Result getRecord(String userToken) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        List<OrderRecord> orderRecordList = orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, user.getId()));
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(orderRecordList).build();
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     *
     * @param userToken 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    @Override
    public Result getGatewayList(String userToken) {
/*        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        List<Gateway> gatewayList = gatewayService.getGatewayList(user.getAccount());*/

        List<Gateway> gatewayList = gatewayService.getGatewayList(0);
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(gatewayList).build();
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     *
     * @param userToken          令牌
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
    public Result createGateway(String userToken, Integer type, String name, String gatewayName, String gatewayReceiptCode, String gatewayAccount) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        GatewayDTO gatewayDTO = GatewayDTO.builder()
                .userId(user.getId())
                .type(type)
                .name(name)
                .gatewayName(gatewayName)
                .gatewayReceiptCode(gatewayReceiptCode)
                .gatewayAccount(gatewayAccount)
                .build();
        gatewayService.createGateway(gatewayDTO);
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param userToken 令牌
     * @param id        收付款資訊ID
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    @Override
    public Result deleteGateway(String userToken, Integer id) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        gatewayService.deleteGateway(user.getId(), id);
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

}
