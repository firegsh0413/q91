package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 用戶訂單VO
 * </p>
 * @author 6687353
 * @since 2023/8/4 14:53:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO extends BaseOrderVO {

    /** 截止轉帳時間 */
    private Long cutOffTime;
    /** 賣方會員暱稱 */
    private String sellerUsername;
    /** 賣方會員uid */
    private Integer sellerId;
    /** 賣方使用的gateway id */
    private Integer sellerGatewayId;
    /** 賣方gateway資訊 */
    private Gateway sellerInfo;

    /** 賣方會員頭像base64 */
    private String sellerAvatar;

    /** 可用收款方式 */
    private List<Integer> availableGateway;

    /** 可用收款方式字串 */
    private String availableGatewayStr;


}
