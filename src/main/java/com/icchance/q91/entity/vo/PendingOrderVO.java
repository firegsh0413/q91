package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrderVO extends BasicOrderVO {

    /** 購買會員暱稱 */
    private String buyerUsername;
    /** 購買會員uid */
    private Integer buyerId;
    /** 購買會員使用的gateway id */
    private Integer buyerGatewayId;
    /** 購買會員gateway資訊 */
    private Gateway buyerInfo;
}
