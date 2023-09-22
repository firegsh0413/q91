package com.icchance.q91.util;

import com.icchance.q91.common.constant.RedisKey;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyUtil {

    public static String generateKey(Integer pendingOrderId) {
        return RedisKey.HEAD + RedisKey.Order.PENDING_ORDER_ID + pendingOrderId;
    }
}
