package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 實名認證DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 14:45:51
 */
@Data
@Builder
public class CertificateDTO {

    private String token;

    private String name;

    private String idNumber;

    private String idCard;

    private String facePhoto;

}
