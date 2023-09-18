package com.icchance.q91.exception;

import cn.hutool.json.JSONUtil;
import com.anji.captcha.util.StringUtils;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.AbstractException;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

    /**
     * <p>
     * 參數效驗異常處理
     * </p>
     * @param req HttpServletRequest
     * @param e  MethodArgumentNotValidException
     * @return com.icchance.q91.common.result.Result<?>
     * @author 6687353
     * @since 2023/9/12 17:10:35
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> parameterMissingExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String detail = StringUtils.EMPTY;
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            if (CollectionUtils.isNotEmpty(errorList)) {
                FieldError fieldError = (FieldError) errorList.get(0);
                detail = fieldError.getField();
            }
        }
        return Result.builder()
                .date(new Date())
                .repCode(ResultCode.PARAM_LOSS.repCode)
                .url(req.getRequestURL().toString())
                .repMsg(ResultCode.PARAM_LOSS.repMsg)
                .detail(detail)
                .repData(detail)
                .build();
    }

    /**
     * <p>
     * 輸入輸出異常處理
     * </p>
     * @author 6687353
     * @since 2023/9/18 15:07:50
     */
    @ExceptionHandler(IOException.class)
    public Result<?> IOExceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        return Result.builder()
                .date(new Date())
                .repCode(ResultCode.PARAM_FORMAT_WRONG.repCode)
                .url(req.getRequestURL().toString())
                .repMsg(ResultCode.PARAM_FORMAT_WRONG.repMsg)
                .detail(e.getMessage())
                .build();
    }

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
            detail = ArrayUtils.isNotEmpty(se.getData()) ? JSONUtil.toJsonStr(se.getData()) : StringUtils.EMPTY;
        } else if (e instanceof MethodArgumentNotValidException) {
            // 參數異常
            MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
            code = ResultCode.PARAM_LOSS.repCode;
            message = ResultCode.PARAM_LOSS.repMsg;
            detail = me.getParameter().getParameterName();
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
