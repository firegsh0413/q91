package com.icchance.q91.controller;

import com.icchance.q91.common.constant.Message;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import lombok.extern.slf4j.Slf4j;

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
    static final String API_PORTAL = "/q91/api/";

    protected final Result SUCCESS = Result.builder().resultCode(ResultCode.SUCCESS.code).msg(Message.SUCCESS.msg).build();

}
