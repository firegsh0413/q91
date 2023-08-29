package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 支付方式DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 17:54:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class GatewayDTO extends BaseDTO {

    /** 使用者ID */
    private Integer userId;
    /** 收付款方式 */
    private Integer type;
    /** 綁定名字 */
    private String name;
    /** 綁定名稱 */
    private String gatewayName;
    /** 收付款號碼 */
    private String gatewayReceiptCode;
    /** 帳號 */
    private String gatewayAccount;
}
