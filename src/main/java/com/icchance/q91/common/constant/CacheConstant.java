package com.icchance.q91.common.constant;

import java.util.concurrent.TimeUnit;

public interface CacheConstant {

    interface Default{
        /** 默認的失效時間,默認不失效 */
        Long TIME_OUT = -1L;
        /** 默認的時間單位,分鐘 */
        TimeUnit TIME_UNIT = TimeUnit.MINUTES;

        String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    }
}
