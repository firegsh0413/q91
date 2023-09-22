package com.icchance.q91.common.constant;

/**
 * <p>
 * Redis key類型
 * </p>
 * @author 6687353
 * @since 2023/9/22 15:56:27
 */
public interface RedisKey {

    String HEAD = "Q91FrontEnd:";

    interface Order {
        String PENDING_ORDER_ID = "PendingOrderId=";
        String ORDER_ID = "OrderId=";
    }

    interface User {
        String BUYER_ID = "BuyerId=";
    }
}
