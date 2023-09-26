package com.icchance.q91.entity.dto;

import com.icchance.q91.common.error.group.FundPassword;
import com.icchance.q91.common.error.group.Password;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用戶個人訊息DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 13:48:54
 */
@Data
@Builder
public class UserInfoDTO {

    private String token;

    private String username;

    private String avatar;

    @NotBlank(groups = Password.class)
    private String oldPassword;
    @NotBlank(groups = Password.class)
    private String newPassword;
    @NotBlank(groups = FundPassword.class)
    private String oldFundPassword;
    @NotBlank(groups = FundPassword.class)
    private String newFundPassword;
}
