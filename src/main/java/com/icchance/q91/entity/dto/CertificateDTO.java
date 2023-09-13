package com.icchance.q91.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

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

    @NotNull
    private String token;

    /** 姓名 */
    @NotNull
    private String name;

    /** 身份證號 */
    @NotNull
    private String idNumber;

    /** 身份證照片 */
    @NotNull
    private String idCard;

    /** 人臉照片 */
    @NotNull
    private String facePhoto;

}
