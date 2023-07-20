package com.icchance.q91.common.constant;

/**
 * <p>
 * 回傳錯誤訊息列舉
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:19:34
 */
public enum Message {
    SUCCESS(0, "SUCCESS"),
    FAILURE(1, "FAILURE"),
    ;

    public final int code;
    public final String msg;
    Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
