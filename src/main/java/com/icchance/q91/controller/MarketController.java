package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 市場控制類
 * </p>
 * @author 6687353
 * @since 2023/8/22 16:01:59
 */
@Slf4j
@RequestMapping(MarketController.PREFIX)
@RestController
public class MarketController extends BaseController {

    static final String PREFIX = "/market";

    private final MarketService marketService;
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    /**
     * <p>
     * 取得市場買賣訊息列表
     * </p>
     * @param token 令牌
     * @param min 最小數量
     * @param max 最大數量
     * @param gatewayType 可用收款方式
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:02:09
     */
    @UserLoginToken
    @GetMapping("/pendingOrder/list")
    public Result getMarketList(@RequestParam String token, @RequestParam(required = false) BigDecimal min,
                                @RequestParam(required = false) BigDecimal max, @RequestParam(required = false) List<Integer> gatewayType) {
        return marketService.getPendingOrderList(token, min, max, gatewayType);
    }

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param token 令牌
     * @param availableGateway 可用收款方式清單
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:04:49
     */
    @UserLoginToken
    @GetMapping("/checkGateway")
    public Result checkGateway(@RequestParam String token, @RequestParam Set<Integer> availableGateway) {
        return marketService.checkGateway(token, availableGateway);
    }

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param token 令牌
     * @param orderId  訂單uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:05
     */
    @UserLoginToken
    @PostMapping("/pendingOrder")
    public Result getPendingOrder(@RequestParam String token, @RequestParam("id") Integer orderId) {
        return marketService.getPendingOrder(token, orderId);
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
    @UserLoginToken
    @PostMapping("/buy")
    public Result buy(@RequestParam String token, @RequestParam("id") Integer orderId, @RequestParam BigDecimal amount, @RequestParam Integer type) {
        return marketService.buy(token, orderId, amount, type);
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
    @UserLoginToken
    @PostMapping("/sell")
    public Result sell(@RequestParam String token, @RequestParam BigDecimal amount, @RequestParam List<Integer> availableGateway) {
        return marketService.sell(token, amount, availableGateway);
    }
}
