package com.icchance.q91.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface OrderConstant {

    enum GatewayTypeEnum {
        BANK_CARD(1, "銀行卡"),
        WE_CHAT(2, "微信"),
        ALIPAY(3, "支付寶"),
        ;

        private final Integer code;
        private final String desc;
        GatewayTypeEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
    }

    static List<Integer> getAllGatewayType() {
        return Arrays.stream(GatewayTypeEnum.values()).map(GatewayTypeEnum::getCode).collect(Collectors.toList());
    }

    /**
     * <p>
     * 訂單狀態列舉
     * </p>
     * @author 6687353
     * @since 2023/7/31 15:26:22
     */
    enum OrderStatusEnum {

        SELL_OR_BUY(1, "出售/購買中"),
        UNCHECK(2, "待確認"),
        APPEAL(3, "申訴中"),
        DONE(4, "已完成"),
        CANCEL(5, "已取消"),

        ;
        private Integer code;
        private String desc;

        OrderStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
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
