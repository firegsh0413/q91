package com.icchance.q91.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
