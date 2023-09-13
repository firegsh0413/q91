package com.icchance.q91.entity.dto;

import com.icchance.q91.common.error.group.Login;
import com.icchance.q91.common.error.group.Register;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(groups = {Register.class, Login.class})
    private String account;
    /** 暱稱 */
    @NotBlank(groups = {Register.class})
    private String username;
    /** 密碼 */
    @NotBlank(groups = {Register.class, Login.class})
    private String password;
    /** 支付密碼 */
    @NotBlank(groups = {Register.class})
    private String fundPassword;
    /** 驗證碼uid */
    private String cId;
    /** 驗證碼結果 */
    private String captcha;

}
