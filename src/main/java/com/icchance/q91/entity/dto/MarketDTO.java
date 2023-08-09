package com.icchance.q91.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MarketDTO {

    private Integer userId;
    private BigDecimal min;
    private BigDecimal max;
    private List<Integer> gatewayType;
}
