package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.CertificateDTO;
import com.icchance.q91.entity.dto.UserDTO;
import com.icchance.q91.entity.dto.UserInfoDTO;
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
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    Result register(UserDTO userDTO);

    /**
     * <p>
     * 登錄
     * </p>
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 09:45:10
     */
    Result login(UserDTO userDTO);

    /**
     * <p>
     * 登出
     * </p>
     * @param baseDTO BaseDTO
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    void logout(BaseDTO baseDTO);

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 18:05:19
     */
    Result getUserInfo(BaseDTO baseDTO);

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:04:37
     */
    Result updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:34:47
     */
    Result getBalance(BaseDTO baseDTO);

    /**
     * <p>
     * 實名認證
     * </p>
     * @param certificateDTO CertificateDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:35:30
     */
    Result certificate(CertificateDTO certificateDTO);

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:49:17
     */
    Result updatePassword(UserInfoDTO userInfoDTO);

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:59:47
     */
    Result updateFundPassword(UserInfoDTO userInfoDTO);

}
