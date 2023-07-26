package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.model.User;

/**
 * <p>
 * 帳號相關服務類
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:54:50
 */
public interface UserService extends IService<User> {

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

    /**
     * <p>
     * 登錄
     * </p>
     * @param account 帳號
     * @param password 密碼
     * @param cId 驗證碼uid
     * @param captcha 驗證碼結果
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 09:45:10
     */
    Result login(String account, String password, String cId, String captcha);

    /**
     * <p>
     * 登出
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    Result logout(String token);

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    Result getUserInfo(String token);

    Result getBalance(String token);

    Result getTransaction(String token);



}
