package com.icchance.q91.common.constant;

/**
 * <p>
 * NSQ topic類型
 * </p>
 * @author 6687353
 * @since 2023/9/20 14:47:20
 */
public enum NsqTopicEnum {

    CHECK_ORDER("CHECK_ORDER"),
    UPLOAD_CERT("UPLOAD_CERT"),
    ;
    private final String value;

    NsqTopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
