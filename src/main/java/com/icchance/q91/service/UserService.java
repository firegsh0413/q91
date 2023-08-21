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
     * @param token 令牌
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    void logout(String token);

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 18:05:19
     */
    Result getUserInfo(String token);

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param token 令牌
     * @param username 用戶名稱
     * @param avatar 用戶頭像
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:04:37
     */
    Result updateUserInfo(String token, String username, String avatar);

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:34:47
     */
    Result getBalance(String token);

    /**
     * <p>
     * 實名認證
     * </p>
     * @param token 令牌
     * @param name 姓名
     * @param idNumber 身份證號
     * @param idCard 身份證照片base64
     * @param facePhoto 人臉識別照片base64
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:35:30
     */
    Result certificate(String token, String name, String idNumber, String idCard, String facePhoto);

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param token 令牌
     * @param oldPassword 原密碼
     * @param newPassword 新密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:49:17
     */
    Result updatePassword(String token, String oldPassword, String newPassword);

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param token 令牌
     * @param oldFundPassword 原交易密碼
     * @param newFundPassword 新交易密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:59:47
     */
    Result updateFundPassword(String token, String oldFundPassword, String newFundPassword);

    /**
     * <p>
     * token取回用戶資訊
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.entity.model.User
     * @author 6687353
     * @since 2023/8/18 18:04:46
     */
    User getUserByToken(String token);

}
