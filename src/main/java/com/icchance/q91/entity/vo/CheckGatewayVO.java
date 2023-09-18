package com.icchance.q91.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 驗證會員收款方式VO
 * </p>
 * @author 6687353
 * @since 2023/9/18 14:39:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckGatewayVO {

    private Integer result;
}
