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

    enum PendingOrderStatusEnum {
        // 掛賣中
        ON_SALE(1, "出售中"),
        // 買家已下單
        ON_ORDER(2, "待確認"),
        NO_PAY(3, "待出款-買家未付款"),
        ALREADY_PAY(4, "待出款-買家已付款"),
        APPEAL(5, "申訴處理"),
        FINISH(6, "成功"),
        CANCEL(7, "已取消"),
        ;
        public final Integer code;
        public final String desc;

        PendingOrderStatusEnum(Integer code, String desc) {
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
        // 已下單，待賣家確認
        ON_ORDER(1, "等待確認"),
        // 賣家已確認，待轉帳
        PAY_UPLOAD(2, "上傳支付"),
        // 已付款，待賣家確認
        ORDER_VERIFY(3, "待入款"),
        APPEAL(4, "申訴處理"),
        FINISH(5, "成功"),
        CANCEL(6, "已取消"),
        ;
        public final Integer code;
        public final String desc;

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
        public final Integer code;
        public final String desc;
        RecordStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
