package com.icchance.q91.exception;

import cn.hutool.json.JSONUtil;
import com.icchance.q91.common.error.AbstractException;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * 全域錯誤管理
 * </p>
 * @author 6687353
 * @since 2023/9/5 15:19:39
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全域錯誤統一訊息格式
     */
    private static final String EXCEPTION_MSG = "handler[{}]; source:[{}]; msg:[{}]; url:[{}]";

    private static final String FAIL = "-1";

    private static final int ERROR_CODE = 502;

    @ExceptionHandler(value = AbstractException.class)
    public Result<?> abstractExceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        String code = FAIL;
        String[] data = null;
        String message = null;
        String detail = null;

        if (e instanceof ServiceException) {
            // 業務邏輯異常
            ServiceException se = (ServiceException) e;
            code = se.getCode();
            data = se.getData();
            message = se.getError();
            detail = ArrayUtils.isNotEmpty(se.getData()) ? JSONUtil.toJsonStr(se.getData()) : "";
        }
        log.error(EXCEPTION_MSG, "AbstractException", e.getClass().getName(), e.getMessage(), req.getRequestURL().toString(), e);
        res.setStatus(ERROR_CODE);
        // 錯誤訊息dialog > title:message, 內容:detail
        return Result.builder()
                .date(new Date())
                .repCode(code)
                .url(req.getRequestURL().toString())
                .repMsg(message)
                .detail(detail)
                .repData(data)
                .build();
    }
}
