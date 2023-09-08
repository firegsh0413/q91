package com.icchance.q91.log;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * p6spy輸出語句格式類
 * </p>
 * @author 6687353
 * @since 2023/9/8 11:57:39
 */
@Slf4j
public class SqlFormatter implements MessageFormattingStrategy {

    /**
     * <p>
     * 自定義格式化SQL輸出
     * </p>
     * @param connectionId 連線設置資料庫ID
     * @param now 當前時間
     * @param elapsed 消耗秒數
     * @param category SQL類別
     * @param prepared SQL語句預編譯內容
     * @param sql 執行的SQL語句
     * @param url 資料庫地址
     * @return java.lang.String
     * @author 6687353
     * @since 2023/9/8 13:17:15
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String logger = "connection " + connectionId + "|" + elapsed + "ms|" + P6Util.singleLine(sql);
        int overTime = 4000;
        if (elapsed > overTime) {
            log.warn(logger);
        }
        return logger;
    }
}
