package com.icchance.q91.entity.vo;

import com.icchance.q91.entity.model.Gateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 基本訂單VO
 * </p>
 * @author 6687353
 * @since 2023/8/4 16:17:59
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseOrderVO {

    /** 掛/訂單uid */
    private Integer id;
    /** 用戶ID */
    private Integer userId;
    /** 掛/訂單狀態 */
    private Integer status;
    /** 建立掛/訂單時間 */
    private LocalDateTime createTime;
    /** 最後操作時間 */
    private LocalDateTime updateTime;
    /** 下單時間 */
    private LocalDateTime tradeTime;
    /** 掛賣金額 */
    private BigDecimal amount;
    /** 掛/訂單編號 */
    private String orderNumber;

    /** 支付憑證圖片base64 */
    private String cert;

}
