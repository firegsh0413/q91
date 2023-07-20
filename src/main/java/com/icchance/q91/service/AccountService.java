package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.vo.AccountVO;
import com.icchance.q91.entity.vo.CaptchaVO;

/**
 * <p>
 * 帳號相關服務類
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:54:50
 */
public interface AccountService {

    /**
     * <p>
     * 取得驗證碼
     * </p>
     * @param account 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 15:25:48
     */
    Result getCaptcha(String account);

    /**
     * <p>
     * 註冊
     * </p>
     * @param account 帳號
     * @param username 暱稱
     * @param password 密碼
     * @param chkPassword 確認密碼
     * @param fundPassword 支付密碼
     * @param chkFundPassword 確認支付密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    Result register(String account, String username, String password, String chkPassword, String fundPassword, String chkFundPassword);
}
