package com.icchance.q91.entity.dto;

import com.icchance.q91.common.error.group.BuyAndSell;
import com.icchance.q91.common.error.group.CheckGateway;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

/**
 * <p>
 * 市場交易資訊DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 16:05:47
 */
@Data
@Builder
public class MarketInfoDTO {

    private String token;

    /** 訂單UID */
    @NotNull(groups = {BuyAndSell.class})
    private Integer id;
    @NotNull(groups = {CheckGateway.class})
    private Set<Integer> availableGateway;
    @NotNull(groups = {BuyAndSell.class})
    private BigDecimal amount;
    @NotNull(groups = {BuyAndSell.class})
    private Integer type;

}
