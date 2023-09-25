package com.icchance.q91.common.constant;

import com.icchance.q91.common.error.ServiceExceptionModel;

/**
 * <p>
 * 訊息代碼列舉
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:27:58
 */
public enum ResultCode implements ServiceExceptionModel {
    // TODO 暫時設置，code不與captcha元件的RepCodeEnum重複
    // TODO 使用0100-5999 7000-9999範圍
    /** 系統類 */
    SUCCESS("0000", "SUCCESS"),
    SYSTEM_ERROR("0002", "系统異常"),
    PARAM_LOSS("0003", "参数有缺"),
    PARAM_FORMAT_WRONG("0004", "参数格式错误"),
    NO_DATA("0005", "查无信息"),
    TRY_REDIS_LOCK_FAILED("0006", "獲取分布式鎖失敗"),

    /** 帳號類 */
    CAPTCHA_ERROR("1000", "验证码错误"),
    ACCOUNT_ALREADY_EXIST("1001", "该账号已存在"),
    ACCOUNT_NOT_EXIST("1002", "账号不存在"),
    ACCOUNT_OR_PASSWORD_WRONG("1003", "账号密码有误"),
    PASSWORD_NOT_MATCH("1004", "密码不匹配"),
    FUND_PASSWORD_NOT_MATCH("1005", "支付密码不匹配"),
    ACCOUNT_NOT_VALID("1006", "6-12位英文大小写或数字字元"),
    USERNAME_NOT_VALId("1007", "1~8位中文或英文大小写"),
    FUND_PASSWORD_NOT_VALID("1008", "6位数字字元"),
    NO_AVAILABLE_GATEWAY("1009", "无可用交易渠道"),
    AWT_PARSE_ERROR("1010", "通行口令无效"),

    /** 訂單交易類 */
    NO_ORDER_EXIST("2000", "查无订单信息"),
    ORDER_LOCK_BY_ANOTHER("2001", "此订单已被其他会员操作"),
    BALANCE_NOT_ENOUGH("2002", "馀额不足"),
    GATEWAY_TYPE_NOT_EXIST("2003", "付款方式不存在"),
    ORDER_FINISH("2004", "订单已完成"),
    BUYER_NOT_PAY("2005", "买家尚未付款"),
    ORDER_IN_TRANSACTION("2006", "此单已在交易中无法操作"),
    NOT_CERTIFICATE("2007", "用户未通过身份认证"),
    ORDER_STATUS_ERROR("2008", "订单状态异常"),

    ;

    public final String repCode;
    public final String repMsg;
    ResultCode(String repCode, String repMsg) {
        this.repCode = repCode;
        this.repMsg = repMsg;
    }

    @Override
    public String getRepCode() {
        return this.repCode;
    }

    @Override
    public String getRepMsg() {
        return this.repMsg;
    }
}
