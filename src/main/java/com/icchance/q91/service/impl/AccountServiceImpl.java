package com.icchance.q91.service.impl;

import com.icchance.q91.common.constant.Message;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.vo.AccountVO;
import com.icchance.q91.entity.vo.CaptchaVO;
import com.icchance.q91.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 帳號相關服務類實做
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:54:50
 */
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * <p>
     * 取得驗證碼
     * </p>
     * @param account 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 15:25:48
     */
    @Override
    public Result getCaptcha(String account) {
        CaptchaVO captchaVO = CaptchaVO.builder()
                .cutoutImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .shadeImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .xAxis(175)
                .yAxis(97)
                .cId("iVBORw0KGgoAAAANSUhEUgAAADc")
                .build();

        return Result.builder()
                .resultCode(ResultCode.SUCCESS)
                .resultMap(captchaVO)
                .build();
    }

    /**
     * <p>
     * 註冊
     * </p>
     *
     * @param account         帳號
     * @param username        暱稱
     * @param password        密碼
     * @param chkPassword     確認密碼
     * @param fundPassword    支付密碼
     * @param chkFundPassword 確認支付密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    @Override
    public Result register(String account, String username, String password, String chkPassword, String fundPassword, String chkFundPassword) {
        // 檢核密碼
        if (!chkPassword.equals(password)) {
            return Result.builder().resultCode(ResultCode.PASSWORD_NOT_MATCH).build();
        }
        if (!chkFundPassword.equals(fundPassword)) {
            return Result.builder().resultCode(ResultCode.FUND_PASSWORD_NOT_MATCH).build();
        }
        return Result.builder().build();
    }
}
