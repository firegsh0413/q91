package com.icchance.q91.controller;

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

    @GetMapping("/list")
    public Result getMarketList(@RequestParam String userToken, @RequestParam(required = false) BigDecimal min,
                                @RequestParam(required = false) BigDecimal max, @RequestParam(required = false) List<Integer> gatewayType) {
        return marketService.getPendingOrderList(userToken, min, max, gatewayType);
    }

    @GetMapping("/checkGateway")
    public Result checkGateway(@RequestParam String userToken, @RequestParam Set<Integer> availableGateway) {
        return marketService.checkGateway(userToken, availableGateway);
    }

    @PostMapping("/pendingOrder")
    public Result getPendingOrder(@RequestParam String userToken, @RequestParam("id") Integer orderId) {
        return marketService.getPendingOrder(userToken, orderId);
    }

    @PostMapping("/buy")
    public Result buy(@RequestParam String userToken, @RequestParam("id") Integer orderId, @RequestParam BigDecimal amount, @RequestParam Integer type) {
        return marketService.buy(userToken, orderId, amount, type);
    }

    @PostMapping("/sell")
    public Result sell(@RequestParam String userToken, @RequestParam BigDecimal amount, @RequestParam List<Integer> availableGateway) {
        return marketService.sell(userToken, amount, availableGateway);
    }
}
