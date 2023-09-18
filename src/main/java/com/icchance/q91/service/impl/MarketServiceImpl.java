package com.icchance.q91.service.impl;

import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.MarketInfoDTO;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.model.OrderRecord;
import com.icchance.q91.entity.model.UserBalance;
import com.icchance.q91.entity.vo.CheckGatewayVO;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.service.*;
import com.icchance.q91.util.JwtUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final OrderRecordService orderRecordService;
    private final UserBalanceService userBalanceService;
    private final JwtUtil jwtUtil;
    public MarketServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService,
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
     * 取得市場買賣訊息列表
     * </p>
     *
     * @param marketDTO MarketDTO
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    @Override
    public List<MarketVO> getPendingOrderList(MarketDTO marketDTO) {
        Integer userId = jwtUtil.parseUserId(marketDTO.getToken());
        marketDTO.setUserId(userId);
        marketDTO.setStatus(OrderConstant.PendingOrderStatusEnum.ON_SALE.code);
        List<MarketVO> pendingOrderList = pendingOrderService.getMarketList(marketDTO);
        if (CollectionUtils.isEmpty(pendingOrderList)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        pendingOrderList.forEach(marketVO -> {
            String availableGatewayStr = marketVO.getAvailableGatewayStr();
            if (StringUtils.isNotEmpty(availableGatewayStr)) {
                marketVO.setAvailableGateway(Arrays.stream(availableGatewayStr.split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
            }
        });
        List<Integer> gatewayType = marketDTO.getGatewayType();
        // 暫時過濾收款方式
        return pendingOrderList.stream().filter(marketVO -> {
            gatewayType.removeAll(marketVO.getAvailableGateway());
            return CollectionUtils.isEmpty(gatewayType);
        }).collect(Collectors.toList());
    }

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:10:40
     */
    @Override
    public CheckGatewayVO checkGateway(MarketInfoDTO marketInfoDTO) {
        Integer userId = jwtUtil.parseUserId(marketInfoDTO.getToken());
        Set<Integer> userAvailableGateway = gatewayService.getAvailableGateway(userId);
        int check = 0;
        for (Integer g : marketInfoDTO.getAvailableGateway()) {
            if (userAvailableGateway.contains(g)) {
                check = 1;
                break;
            }
        }
        return CheckGatewayVO.builder().result(check).build();
    }

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param marketDTO MarketDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:37
     */
    @Override
    public MarketVO getPendingOrder(MarketDTO marketDTO) {
        Integer userId = jwtUtil.parseUserId(marketDTO.getToken());
        marketDTO.setUserId(userId);
        List<MarketVO> pendingOrderList = pendingOrderService.getMarketList(marketDTO);
        if (CollectionUtils.isEmpty(pendingOrderList)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        pendingOrderList.forEach(marketVO -> {
            String availableGatewayStr = marketVO.getAvailableGatewayStr();
            if (StringUtils.isNotEmpty(availableGatewayStr)) {
                marketVO.setAvailableGateway(Arrays.stream(availableGatewayStr.split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
            }
        });
        return pendingOrderList.get(0);
    }

    /**
     * <p>
     * 購買
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:12:14
     */
    @Override
    public void buy(MarketInfoDTO marketInfoDTO) {
        Integer userId = jwtUtil.parseUserId(marketInfoDTO.getToken());
        // 1.驗證是否該掛單已被其他會員操作購買鎖定
        MarketVO pendingOrder = pendingOrderService.getMarketDetail(null, marketInfoDTO.getId());
        if (Objects.isNull(pendingOrder)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 掛單狀態不為掛賣中
        if (!OrderConstant.PendingOrderStatusEnum.ON_SALE.code.equals(pendingOrder.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_LOCK_BY_ANOTHER);
        }
        Gateway buyerGateway = gatewayService.getGatewayByType(userId, marketInfoDTO.getType());
        if (Objects.isNull(buyerGateway)) {
            throw new ServiceException(ResultCode.GATEWAY_TYPE_NOT_EXIST);
        }
        if (marketInfoDTO.getAmount().compareTo(pendingOrder.getAmount()) > 0) {
            throw new ServiceException(ResultCode.BALANCE_NOT_ENOUGH);
        }
        Gateway sellerGateway = gatewayService.getGatewayByType(pendingOrder.getUserId(), marketInfoDTO.getType());
        BigDecimal amount = marketInfoDTO.getAmount();
        // 2-1.建立訂單
        // cutoffTime為tradeTime往後十分鐘
        LocalDateTime tradeTime = LocalDateTime.now();
        OrderDTO orderDTO = OrderDTO.builder()
                .userId(userId)
                .pendingOrderId(marketInfoDTO.getId())
                .sellerId(pendingOrder.getSellerId())
                .buyerGatewayId(Optional.of(buyerGateway).map(Gateway::getId).orElse(null))
                .sellerGatewayId(Optional.ofNullable(sellerGateway).map(Gateway::getId).orElse(null))
                .amount(amount)
                .tradeTime(tradeTime)
                //.cutOffTime(tradeTime.plusMinutes(10))
                .build();
        Order order = orderService.create(orderDTO);
        // 2-2.更新掛單狀態
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .id(marketInfoDTO.getId())
                .status(OrderConstant.PendingOrderStatusEnum.ON_ORDER.code)
                .buyerId(userId)
                .buyerGatewayId(Optional.of(buyerGateway).map(Gateway::getId).orElse(null))
                .orderId(order.getId())
                .sellerGatewayId(Optional.ofNullable(sellerGateway).map(Gateway::getId).orElse(null))
                .tradeTime(tradeTime)
                .build();
        pendingOrderService.update(pendingOrderDTO);
        // 3.更新買方錢包 交易中+額度
        UserBalance buyerBalance = userBalanceService.getEntity(userId);
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().add(amount));
        userBalanceService.updateEntity(buyerBalance);
        // 4.建立訂單(錢包)紀錄
        OrderRecord orderRecord = OrderRecord.builder()
                .userId(userId)
                .status(OrderConstant.RecordStatusEnum.MARKET_BUY.code)
                .amount(amount)
                .orderNumber(order.getOrderNumber())
                .createTime(tradeTime)
                .build();
        orderRecordService.save(orderRecord);
    }

    /**
     * <p>
     * 出售
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    @Override
    public void sell(MarketInfoDTO marketInfoDTO) {
        Integer userId = jwtUtil.parseUserId(marketInfoDTO.getToken());
        BigDecimal amount = marketInfoDTO.getAmount();
        // 1.建立掛單
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .userId(userId)
                .amount(amount)
                .availableGatewayStr(marketInfoDTO.getAvailableGateway().stream().map(Object::toString).collect(Collectors.joining(",")))
                .build();
        String orderNumber = pendingOrderService.create(pendingOrderDTO);

        // 2.更新賣方錢包
        // 可售數量->賣單餘額
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().add(amount));
        if (amount.compareTo(sellerBalance.getAvailableAmount()) > 0) {
            throw new ServiceException(ResultCode.BALANCE_NOT_ENOUGH);
        }
        sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().subtract(amount));
        userBalanceService.updateEntity(sellerBalance);
        // 3.建立訂單紀錄
        OrderRecord orderRecord = OrderRecord.builder()
                .userId(userId)
                .status(OrderConstant.RecordStatusEnum.MARKET_SELL.code)
                .amount(amount)
                .orderNumber(orderNumber)
                .createTime(LocalDateTime.now())
                .build();
        orderRecordService.save(orderRecord);
    }
}
