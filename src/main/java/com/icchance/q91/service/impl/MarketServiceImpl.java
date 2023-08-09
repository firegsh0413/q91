package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.service.GatewayService;
import com.icchance.q91.service.MarketService;
import com.icchance.q91.service.PendingOrderService;
import com.icchance.q91.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MarketServiceImpl implements MarketService {

    private final UserService userService;
    private final GatewayService gatewayService;
    private final PendingOrderService pendingOrderService;
    public MarketServiceImpl(UserService userService, GatewayService gatewayService, PendingOrderService pendingOrderService) {
        this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
    }

    /**
     * <p>
     * 取得市場買賣訊息列表
     * </p>
     *
     * @param userToken   使用者令牌
     * @param min         最小幣數量
     * @param max         最大幣數量
     * @param gatewayType 收款方式
     * @return java.util.List<com.icchance.q91.entity.vo.MarketVO>
     * @author 6687353
     * @since 2023/8/4 16:35:34
     */
    @Override
    public Result getPendingOrderList(String userToken, BigDecimal min, BigDecimal max, List<Integer> gatewayType) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        MarketDTO marketDTO = MarketDTO.builder().userId(user.getId()).min(min).max(max).gatewayType(gatewayType).build();
        List<MarketVO> marketList = pendingOrderService.getPendingOrderList(marketDTO);
        if (CollectionUtils.isNotEmpty(marketList)) {
            marketList.forEach(marketVO -> {
                String availableGatewayStr = marketVO.getAvailableGatewayStr();
                if (StringUtils.isNotEmpty(availableGatewayStr)) {
                    marketVO.setAvailableGateway(Arrays.stream(availableGatewayStr.split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
                }
            });
        }
        List<MarketVO> result = marketList.stream().filter(marketVO -> {
            gatewayType.removeAll(marketVO.getAvailableGateway());
            return CollectionUtils.isNotEmpty(gatewayType);
        }).collect(Collectors.toList());
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(result).build();
    }

    @Override
    public Result checkGateway(String userToken, Set<Integer> availableGateway) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        Set<Integer> userAvailableGateway = gatewayService.getAvailableGateway(user.getId());
        boolean check = Boolean.FALSE;
        for (Integer g : availableGateway) {
            if (userAvailableGateway.contains(g)) {
                check = Boolean.TRUE;
                break;
            }
        }
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(check).build();
    }

    @Override
    public Result getPendingOrder(String userToken, Integer orderId) {
        User user = userService.getUserByToken(userToken);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(pendingOrderService.getPendingOrder(user.getId(), orderId))
                .build();
    }
}
