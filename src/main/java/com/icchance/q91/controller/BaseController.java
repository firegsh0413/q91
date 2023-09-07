package com.icchance.q91.controller;

import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

/**
 * <p>
 * 基礎控制類
 * </p>
 * @author 6687353
 * @since 2023/7/19 13:17:50
 */
@Slf4j
public class BaseController {

    //static final String API_PORTAL = "https://api-test.q91.co";
    protected final Result<Void> SUCCESS = Result.<Void>builder().repCode(ResultCode.SUCCESS.getRepCode()).repMsg(ResultCode.SUCCESS.repMsg).build();

    protected Result.ResultBuilder SUCCESS_DATA = Result.<T>builder().repCode(ResultCode.SUCCESS.getRepCode()).repMsg(ResultCode.SUCCESS.repMsg);

}
