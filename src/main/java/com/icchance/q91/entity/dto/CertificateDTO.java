package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 實名認證DTO
 * </p>
 * @author 6687353
 * @since 2023/8/29 14:45:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CertificateDTO extends BaseDTO {

    private String name;

    private String idNumber;

    private String idCard;

    private String facePhoto;
}
