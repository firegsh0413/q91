package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserCertificateAnnotation;
import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.error.group.CheckGateway;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.dto.MarketInfoDTO;
import com.icchance.q91.entity.vo.CheckGatewayVO;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/pendingOrder/list")
    public Result<List<MarketVO>> getMarketList(@RequestBody MarketDTO marketDTO) {
        return SUCCESS_DATA.repData(marketService.getPendingOrderList(marketDTO)).build();
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
    @PostMapping("/checkGateway")
    public Result<CheckGatewayVO> checkGateway(@RequestBody @Validated({CheckGateway.class}) MarketInfoDTO marketInfoDTO) {
        return SUCCESS_DATA.repData(marketService.checkGateway(marketInfoDTO)).build();
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
    @PostMapping("/pendingOrder")
    public Result<MarketVO> getPendingOrder(@RequestBody MarketDTO marketDTO) {
        return SUCCESS_DATA.repData(marketService.getPendingOrder(marketDTO)).build();
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
    @UserCertificateAnnotation
    @PostMapping("/buy")
    public Result<Void> buy(@RequestBody MarketInfoDTO marketInfoDTO) {
        marketService.buy(marketInfoDTO);
        return SUCCESS;
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
    @UserCertificateAnnotation
    @PostMapping("/sell")
    public Result<Void> sell(@RequestBody MarketInfoDTO marketInfoDTO) {
        marketService.sell(marketInfoDTO);
        return SUCCESS;
    }
}
