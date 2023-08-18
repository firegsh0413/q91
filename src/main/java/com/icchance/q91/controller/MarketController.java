package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Slf4j
@RequestMapping(MarketController.PREFIX)
@RestController
public class MarketController extends BaseController {

    static final String PREFIX = "/market";

    private final MarketService marketService;
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @UserLoginToken
    @GetMapping("/list")
    public Result getMarketList(@RequestParam String token, @RequestParam(required = false) BigDecimal min,
                                @RequestParam(required = false) BigDecimal max, @RequestParam(required = false) List<Integer> gatewayType) {
        return marketService.getPendingOrderList(token, min, max, gatewayType);
    }

    @UserLoginToken
    @GetMapping("/checkGateway")
    public Result checkGateway(@RequestParam String token, @RequestParam Set<Integer> availableGateway) {
        return marketService.checkGateway(token, availableGateway);
    }

    @UserLoginToken
    @PostMapping("/pendingOrder")
    public Result getPendingOrder(@RequestParam String token, @RequestParam("id") Integer orderId) {
        return marketService.getPendingOrder(token, orderId);
    }

    @UserLoginToken
    @PostMapping("/buy")
    public Result buy(@RequestParam String token, @RequestParam("id") Integer orderId, @RequestParam BigDecimal amount, @RequestParam Integer type) {
        return marketService.buy(token, orderId, amount, type);
    }

    @UserLoginToken
    @PostMapping("/sell")
    public Result sell(@RequestParam String token, @RequestParam BigDecimal amount, @RequestParam List<Integer> availableGateway) {
        return marketService.sell(token, amount, availableGateway);
    }
}
