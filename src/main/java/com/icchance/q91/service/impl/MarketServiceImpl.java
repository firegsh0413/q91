package com.icchance.q91.service.impl;

import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeTransactionDB;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 市場服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/22 16:03:54
 */
@Service
public class MarketServiceImpl implements MarketService {

    private final UserService userService;
    private final GatewayService gatewayService;
    private final PendingOrderService pendingOrderService;
    private final OrderService orderService;
    private final FakeTransactionDB fakeTransactionDB;
    public MarketServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService,
                             OrderService orderService, FakeTransactionDB fakeTransactionDB) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.fakeTransactionDB = fakeTransactionDB;
    }

    /**
     * <p>
     * 取得市場買賣訊息列表
     * </p>
     *
     * @param token   使用者令牌
     * @param min         最小幣數量
     * @param max         最大幣數量
     * @param gatewayType 收款方式
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    @Override
    public Result getPendingOrderList(String token, BigDecimal min, BigDecimal max, List<Integer> gatewayType) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        MarketDTO marketDTO = MarketDTO.builder().userId(user.getId()).min(min).max(max).gatewayType(gatewayType)
                .status(OrderConstant.OrderStatusEnum.SELL_OR_BUY.getCode()).build();
        List<MarketVO> pendingOrderList = pendingOrderService.getPendingOrderList(marketDTO);
        if (CollectionUtils.isNotEmpty(pendingOrderList)) {
            pendingOrderList.forEach(marketVO -> {
                String availableGatewayStr = marketVO.getAvailableGatewayStr();
                if (StringUtils.isNotEmpty(availableGatewayStr)) {
                    marketVO.setAvailableGateway(Arrays.stream(availableGatewayStr.split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
                }
            });
        }
        // 暫時過濾收款方式
        List<MarketVO> result = pendingOrderList.stream().filter(marketVO -> {
            gatewayType.removeAll(marketVO.getAvailableGateway());
            return CollectionUtils.isNotEmpty(gatewayType);
        }).collect(Collectors.toList());
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(result).build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(fakeTransactionDB.getMarketPendingOrderList()).build();
    }

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param token 令牌
     * @param availableGateway 可用收款方式清單
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:10:40
     */
    @Override
    public Result checkGateway(String token, Set<Integer> availableGateway) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        Set<Integer> userAvailableGateway = gatewayService.getAvailableGateway(user.getId());
        int check = 0;
        for (Integer g : availableGateway) {
            if (userAvailableGateway.contains(g)) {
                check = 1;
                break;
            }
        }
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(check).build();*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(1).build();
    }

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param token 令牌
     * @param orderId  訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:37
     */
    @Override
    public Result getPendingOrder(String token, Integer orderId) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        MarketDTO marketDTO = MarketDTO.builder().userId(user.getId()).orderId(orderId).build();
        List<MarketVO> pendingOrderList = pendingOrderService.getPendingOrderList(marketDTO);
        if (CollectionUtils.isNotEmpty(pendingOrderList)) {
            pendingOrderList.forEach(marketVO -> {
                String availableGatewayStr = marketVO.getAvailableGatewayStr();
                if (StringUtils.isNotEmpty(availableGatewayStr)) {
                    marketVO.setAvailableGateway(Arrays.stream(availableGatewayStr.split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
                }
            });
            return Result.builder().resultCode(ResultCode.SUCCESS)
                    .resultMap(pendingOrderList.get(0))
                    .build();
        }*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(fakeTransactionDB.getPendingOrder()).build();
    }

    /**
     * <p>
     * 購買
     * </p>
     * @param token 令牌
     * @param orderId 訂單uid
     * @param amount 數量
     * @param type 付款方式
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:12:14
     */
    @Override
    public Result buy(String token, Integer orderId, BigDecimal amount, Integer type) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        // 1.驗證是否該掛單已被其他會員操作購買鎖定
        // 訂單狀態不為出售中
        MarketVO pendingOrder = pendingOrderService.getPendingOrder(user.getId(), orderId);
        if (!OrderConstant.OrderStatusEnum.SELL_OR_BUY.getCode().equals(pendingOrder.getStatus())) {
            return Result.builder().resultCode(ResultCode.ORDER_LOCK_BY_ANOTHER).build();
        }
        Gateway buyerGateway = gatewayService.getGatewayByType(user.getId(), type);
        Gateway sellerGateway = gatewayService.getGatewayByType(pendingOrder.getUserId(), type);

        OrderDTO orderDTO = OrderDTO.builder().userId(user.getId())
                .pendingOrderId(orderId)
                .sellerId(pendingOrder.getSellerId())
                .userId(user.getId())
                .status(OrderConstant.OrderStatusEnum.SELL_OR_BUY.getCode())
                .buyerGatewayId(Optional.ofNullable(buyerGateway).map(Gateway::getId).orElse(null))
                .sellerGatewayId(Optional.ofNullable(sellerGateway).map(Gateway::getId).orElse(null))
                .amount(amount)
                .build();
        // 2.建立訂單，更新掛單狀態
        orderService.createOrder(orderDTO);
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder().id(orderId)
                .status(OrderConstant.OrderStatusEnum.UNCHECK.getCode())
                .buyerId(user.getId())
                .buyerGatewayId(Optional.ofNullable(buyerGateway).map(Gateway::getId).orElse(null))
                .sellerGatewayId(Optional.ofNullable(sellerGateway).map(Gateway::getId).orElse(null))
                .build();
        // cutoffTime為tradeTime往後十分鐘

        pendingOrderService.uploadPendingOrder(pendingOrderDTO);*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 出售
     * </p>
     * @param token 令牌
     * @param amount 數量
     * @param availableGateway 可用收款方式
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    @Override
    public Result sell(String token, BigDecimal amount, List<Integer> availableGateway) {
/*        User user = userService.getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        // 建立掛單
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                //.userId(user.getId())
                .status(OrderConstant.OrderStatusEnum.SELL_OR_BUY.getCode())
                .amount(amount)
                .availableGatewayStr(availableGateway.stream().map(Object::toString).collect(Collectors.joining(",")))
                .build();
        pendingOrderService.createPendingOrder(pendingOrderDTO);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }
}
