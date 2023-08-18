package com.icchance.q91.common.constant;

public interface MessageConstant {

    public static final Integer IS_READ_FALSE = 0;
    public static final Integer IS_READ_TRUE = 1;
    public static final Integer IS_NOTICE_FALSE = 0;
    public static final Integer IS_NOTICE_TRUE = 1;

    enum PrivateMessageType {

        NORMAL(1, "一般訊息"),
        SYSTEM(2, "系統訊息"),
        EMERGENCY(3, "緊急訊息"),
        DISCOUNT(4, "優惠訊息"),
        ;

        private Integer code;
        private String desc;
        PrivateMessageType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }

    }
}
