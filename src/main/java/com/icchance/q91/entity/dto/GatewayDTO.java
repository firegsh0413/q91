package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayDTO {

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
