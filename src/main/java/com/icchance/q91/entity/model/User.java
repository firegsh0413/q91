package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用戶資訊
 * </p>
 * @author 6687353
 * @since 2023/7/26 14:45:55
 */
@Data
@TableName("USER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1036384808450084875L;

    @TableId(value = "ID")
    private Integer id;
    @TableField(value = "ACCOUNT")
    private String account;
    @TableField(value = "USERNAME")
    private String username;
    @TableField(value = "PASSWORD")
    private String password;
    @TableField(value = "FUND_PASSWORD")
    private String fundPassword;
    @TableField(value = "ADDRESS")
    private String address;
    @TableField(value = "CERTIFIED")
    private Boolean certified;
    @TableField(value = "NAME")
    private String name;
    @TableField(value = "ID_NUMBER")
    private String idNumber;
    @TableField(value = "ID_CARD")
    private String idCard;
    @TableField(value = "FACE_PHOTO")
    private String facePhoto;
    @TableField(value = "ROLE")
    private Integer role;
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;
}
