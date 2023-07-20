package com.icchance.q91.common.constant;

/**
 * <p>
 * 訊息代碼列舉
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:27:58
 */
public enum ResultCode {
    /** 系統類 */
    SUCCESS("000", "SUCCESS"),
    SYSTEM_UNDER_MAINTAIN("002", "系统维护"),
    PARAM_LOSS("001", "参数有缺"),
    PARAM_FORMAT_WRONG("", "参数格式错误"),



    /** 帳號類 */
    CAPTCHA_ERROR("003", "验证码错误"),
    ACCOUNT_ALREADY_EXIST("004", "账号已存在"),
    ACCOUNT_NOT_EXIST("005", "账号不存在"),
    ACCOUNT_OR_PASSWORD_WRONG("", "账号密码有误"),
    PASSWORD_NOT_MATCH("", "密码不匹配"),
    FUND_PASSWORD_NOT_MATCH("", "支付密码不匹配"),
    ;

    public final String code;
    public final String msg;
    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
