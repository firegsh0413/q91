package com.icchance.q91.common.error;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Getter
@Setter
public class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 8696471798520302033L;

    /**
     * 堆棧信息陣列(數組)打印初始行數
     * */
    private static final int STACK_TRACE_LIMIT_FROM = 0;

    /**
     * 堆棧信息限制(數組)打印最終行數
     * */
    private static final int STACK_TRACE_LIMIT_TO = 10;

    private static final int FAIL = -1;

    /**
     * 錯誤碼
     */
    private int code;
    /**
     * 錯誤消息
     */
    private String error;
    /**
     * 錯誤資料
     */
    private String[] data;

    AbstractException(String error) {
        super(error);
        this.code = FAIL;
        this.error = error;
        this.printStackTrace(error);
    }

    AbstractException(Exception e) {
        super(e);
        this.code = FAIL;
        this.error = e.getMessage();
        this.printStackTrace(e.getMessage());
    }

    AbstractException(int code, String error, String... data) {
        super(fillParam(error, data));
        this.code = code;
        this.data = data;
        this.error = fillParam(error, data);
        this.printStackTrace(fillParam(error, data));
    }

    AbstractException(String error, String... data) {
        super(fillParam(error, data));
        this.code = FAIL;
        this.data = data;
        this.error = fillParam(error, data);
        this.printStackTrace(fillParam(error, data));
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    private static String fillParam(String errorMsg, final String... inArr) {
        String[] symbols = new String[]{"{}", "%s"};
        for (String symbol : symbols) {
            errorMsg = replaceBySymbol(errorMsg, symbol, inArr);
        }
        return errorMsg;
    }

    private static String replaceBySymbol(String errorMsg, String symbol, String... inArr) {
        if (ArrayUtil.isEmpty(inArr)) {
            return errorMsg;
        }
        int symbolLen = StringUtils.countMatches(errorMsg, symbol);
        // 輸入陣列長度不足，補足空字串
        if (symbolLen > inArr.length) {
            for (int i = 0; i < symbolLen - inArr.length; i++) {
                inArr = ArrayUtil.append(inArr, "");
            }
        }
        // 循環替換字串
        for (int i = 0; i < symbolLen; i++) {
            errorMsg = StringUtils.replaceOnce(errorMsg, symbol, inArr[i]);
        }
        return errorMsg;
    }

    public void printStackTrace(String errorMsg) {
        LoggerFactory.getLogger(this.getClass()).error(errorMsg);
        StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
        StackTraceElement[] stackTraceElementArr = Arrays.copyOfRange(stackTrace, STACK_TRACE_LIMIT_FROM, Math.min(stackTrace.length, STACK_TRACE_LIMIT_TO));
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            LoggerFactory.getLogger(this.getClass()).error(String.valueOf(stackTraceElement));
        }
    }
}
