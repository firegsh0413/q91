package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用戶個人訊息DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 13:48:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserInfoDTO extends BaseDTO {

    private String username;

    private String avatar;

    private String oldPassword;

    private String newPassword;

    private String oldFundPassword;

    private String newFundPassword;
}
