package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 用戶資訊entity
 * </p>
 * @author 6687353
 * @since 2023/7/26 14:45:55
 */
@Data
@TableName("user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1036384808450084875L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String account;

    private String username;

    private String password;

    private String fundPassword;

    private String address;

    private Boolean certified;

    private String name;

    private String idNumber;

    private String idCard;

    private String facePhoto;

    private Integer role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String avatar;
}
