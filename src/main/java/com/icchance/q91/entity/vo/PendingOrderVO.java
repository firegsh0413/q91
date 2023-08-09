package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    //private Gateway buyerInfo;
}
