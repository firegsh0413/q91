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
public class OrderVO extends BasicOrderVO {

    /** 截止轉帳時間 */
    private Long cutOffTime;
    /** 出售會員暱稱 */
    private String sellerUsername;
    /** 出售會員uid */
    private Integer sellerId;
    /** 出售會員使用的gateway id */
    private Integer sellerGatewayId;
    /** 出售會員gateway資訊 */
    private Gateway sellerInfo;
}
