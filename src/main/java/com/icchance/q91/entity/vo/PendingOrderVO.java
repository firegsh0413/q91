package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.*;

import java.util.Set;

/**
 * <p>
 * 用戶掛單VO
 * </p>
 * @author 6687353
 * @since 2023/8/4 14:53:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrderVO extends BaseOrderVO {

    /** 買方會員暱稱 */
    private String buyerUsername;
    /** 買方會員uid */
    private Integer buyerId;
    /** 買方會員使用的gateway id */
    private Integer buyerGatewayId;
    /** 買方會員gateway資訊 */
    private Gateway buyerInfo;
    /** 賣方使用的gateway id */
    private Integer sellerGatewayId;
    /** 賣方gateway資訊 */
    private Gateway sellerInfo;
    /** 可用收款方式 */
    private Set<Integer> availableGateway;
    /** 可用收款方式字串 */
    private String availableGatewayStr;
}
