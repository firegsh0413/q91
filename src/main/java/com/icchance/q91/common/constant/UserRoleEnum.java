package com.icchance.q91.common.constant;

public enum UserRoleEnum {

    USER(0, "一般使用者"),
    BUY_BUS(1, "官方買家"),
    SELL_BUS(2, "官方賣家"),
    ;
    UserRoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private Integer code;
    private String desc;
}
