package com.icchance.q91.common.constant;

public interface OrderConstant {

    enum GatewayTypeEnum {
        BANK_CARD(0, "銀行卡"),
        WE_CHAT(1, "微信"),
        ALIPAY(2, "支付寶"),
        ;

        private Integer code;
        private String desc;
        GatewayTypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * <p>
     * 訂單狀態列舉
     * </p>
     * @author 6687353
     * @since 2023/7/31 15:26:22
     */
    enum OrderStatusEnum {

        SELL(0, "出售中"),
        UNCHECK(1, "待確認"),
        APPEAL(2, "申訴中"),
        DONE(3, "已完成"),
        CANCEL(4, "已取消"),

        ;
        private Integer code;
        private String desc;

        OrderStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * <p>
     * 異動狀態
     * </p>
     * @author 6687353
     * @since 2023/8/2 14:04:02
     */
    enum RecordStatusEnum {
        MARKET_BUY(1, "市場買單"),
        MARKET_SELL(2, "市場賣單"),
        ADD_CREDIT(3, "充值到商戶"),
        PAY_OUT(4, "商戶下發入"),
        ;
        private Integer code;
        private String desc;
        RecordStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
