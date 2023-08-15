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
public interface UserService {

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
     * @param fundPassword 支付密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    Result register(String account, String username, String password, String fundPassword);

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
     * @param userToken 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    Result logout(String userToken);

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param userToken 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 18:05:19
     */
    Result getUserInfo(String userToken);

    Result updateUserInfo(String userToken, String username, String avatar);
    Result getBalance(String userToken);
    Result certificate(String userToken, String name, String idNumber, String idCard, String facePhoto);
    Result updatePassword(String userToken, String oldPassword, String newPassword);
    Result updateFundPassword(String userToken, String oldFundPassword, String newFundPassword);
    User getUserByToken(String token);

}
