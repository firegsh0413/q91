package com.icchance.q91.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用戶認證VO
 * </p>
 * @author 6687353
 * @since 2023/9/18 14:39:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCertificateVO {

    private String account;
    private String name;
    private String idNumber;
    private String idCard;
    private String facePhoto;
}
