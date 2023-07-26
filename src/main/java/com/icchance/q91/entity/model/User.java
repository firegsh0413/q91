package com.icchance.q91.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("USER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1036384808450084875L;

    @TableId(value = "ACCOUNT")
    private String account;
    @TableField(value = "USERNAME")
    private String username;
    @TableField(value = "PASSWORD")
    private String password;
    @TableField(value = "FUND_PASSWORD")
    private String fundPassword;
    @TableField(value = "ADDRESS")
    private String address;

}
