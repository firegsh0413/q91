package com.icchance.q91.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 帳號DTO
 * </p>
 * @author 6687353
 * @since 2023/7/20 16:15:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /** 帳號 */
    private String account;
    /** 暱稱 */
    private String username;
    /** 密碼 */
    private String password;
    /** 支付密碼 */
    private String fundPassword;
    /** 驗證碼uid */
    private String cId;
    /** 驗證碼結果 */
    private String captcha;

}
