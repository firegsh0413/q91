package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用戶辨識資訊
 * </p>
 * @author 6687353
 * @since 2023/7/26 14:45:43
 */
@Data
@TableName("USER_CERTIFICATE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCerificate implements Serializable {

    private static final long serialVersionUID = 2331084791414390739L;

    @TableId(value = "ACCOUNT")
    private String account;
    @TableField(value = "NAME")
    private String name;
    @TableField(value = "ID_NUMBER")
    private String idNumber;
    @TableField(value = "ID_CARD")
    private String idCard;
    @TableField(value = "FACE_PHOTO")
    private String facePhoto;
}
