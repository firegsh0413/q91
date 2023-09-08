package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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

    private Integer userId;
    /** 訂單UID */
    private Integer id;

    private Set<Integer> availableGateway;

    private BigDecimal amount;

    private Integer type;

}
