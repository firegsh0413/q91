package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketVO extends BaseOrderVO {

    /** 賣方會員uid */
    private Integer sellerId;
    /** 賣方會員暱稱 */
    private String sellerUsername;
    /** 賣方會員頭像 */
    private String sellerAvatar;
    /** 賣方可用收款方式 */
    private Set<Integer> availableGateway;
    /** 賣方收付款資訊清單 */
    //private List<Gateway> gatewayList;
    /** 賣方可用收款方式 字串 */
    private String availableGatewayStr;

}
