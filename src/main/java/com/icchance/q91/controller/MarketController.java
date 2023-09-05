package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.MarketInfoDTO;
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
     * @param marketDTO MarketDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:02:09
     */
    @UserLoginToken
    @GetMapping("/pendingOrder/list")
    public Result getMarketList(@RequestBody MarketDTO marketDTO) {
        return marketService.getPendingOrderList(marketDTO);
    }

    /**
     * <p>
     * 驗證會員是否有收款方式
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:04:49
     */
    @UserLoginToken
    @GetMapping("/checkGateway")
    public Result checkGateway(@RequestBody MarketInfoDTO marketInfoDTO) {
        return marketService.checkGateway(marketInfoDTO);
    }

    /**
     * <p>
     * 取得賣方掛單訊息
     * </p>
     * @param marketDTO MarketDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:11:05
     */
    @UserLoginToken
    @GetMapping("/pendingOrder")
    public Result getPendingOrder(@RequestBody MarketDTO marketDTO) {
        return marketService.getPendingOrder(marketDTO);
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
    @UserLoginToken
    @PostMapping("/buy")
    public Result buy(@RequestBody MarketInfoDTO marketInfoDTO) {
        return marketService.buy(marketInfoDTO);
    }

    /**
     * <p>
     * 出售
     * </p>
     * @param marketInfoDTO MarketInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/22 16:15:27
     */
    @UserLoginToken
    @PostMapping("/sell")
    public Result sell(@RequestBody MarketInfoDTO marketInfoDTO) {
        return marketService.sell(marketInfoDTO);
    }
}
