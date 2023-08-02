package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BasicOrderVO {

    /** 掛單/訂單uid */
    private Integer id;
    /** 用戶ID */
    private Integer userId;
    /** 掛單狀態 */
    private Integer status;
    /** 建立掛單時間 */
    private LocalDateTime createTime;
    /** 最後操作時間 */
    private LocalDateTime updateTime;
    /** 下單時間 */
    private LocalDateTime tradeTime;
    /** 掛賣金額 */
    private BigDecimal amount;
    /** 掛單編號 */
    private String orderNumber;
    /** 可用收款方式清單 */
    private List<Gateway> availableTradeInfo;
    /** 收款uid */
    private Integer tradeInfoId;
    /** 購買會員收款方式 */
    private Gateway buyerInfo;
    /** 支付憑證圖片base64 */
    private String cert;

}
