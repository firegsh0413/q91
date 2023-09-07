package com.icchance.q91.controller;

import com.icchance.q91.annotation.PassToken;
import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.*;
import com.icchance.q91.entity.vo.UserBalanceVO;
import com.icchance.q91.entity.vo.UserVO;
import com.icchance.q91.service.UserBalanceService;
import com.icchance.q91.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 帳號相關控制類
 * </p>
 * @author 6687353
 * @since 2023/7/19 13:28:00
 */
@Slf4j
@RequestMapping(UserController.PREFIX)
@RestController
public class UserController extends BaseController {

    private final UserService userService;

    private final UserBalanceService userBalanceService;

    public UserController(UserService userService, UserBalanceService userBalanceService) {
        this.userService = userService;
        this.userBalanceService = userBalanceService;
    }

    static final String PREFIX = "/user";

    /**
     * <p>
     * 註冊
     * </p>
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 10:05:17
     */
    @PassToken
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserDTO userDTO) {
        return Result.<UserVO>builder().repData(userService.register(userDTO)).build();
    }

    /**
     * <p>
     * 登錄
     * </p>
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 10:09:52
     */
    @PassToken
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserDTO userDTO) {
        return SUCCESS_DATA.repData(userService.login(userDTO)).build();
    }

    /**
     * <p>
     * 登出
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 12:00:18
     */
    @UserLoginToken
    @PostMapping("/logout")
    public Result<Void> logout(@RequestBody BaseDTO baseDTO) {
        userService.logout(baseDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    @UserLoginToken
    @GetMapping("/info")
    public Result<Void> getUserInfo(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(userService.getUserInfo(baseDTO)).build();
    }

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 09:47:52
     */
    @UserLoginToken
    @GetMapping("/balance")
    public Result<UserBalanceVO> getBalance(@RequestBody BaseDTO baseDTO) {
        return SUCCESS_DATA.repData(userService.getBalance(baseDTO)).build();
    }

    /**
     * <p>
     * 實名認證
     * </p>
     * @param certificateDTO CertificateDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 09:56:33
     */
    @UserLoginToken
    @PostMapping("/certificate")
    public Result<Void> certificate(@RequestBody CertificateDTO certificateDTO) {
        userService.certificate(certificateDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:48:26
     */
    @UserLoginToken
    @PostMapping("/pwd/update")
    public Result<Void> updatePassword(@RequestBody UserInfoDTO userInfoDTO) {
        userService.updatePassword(userInfoDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:00:04
     */
    @UserLoginToken
    @PostMapping("/fundPwd/update")
    public Result<Void> updateFundPassword(@RequestBody UserInfoDTO userInfoDTO) {
        userService.updateFundPassword(userInfoDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:03:37
     */
    @UserLoginToken
    @PostMapping("/info/update")
    public Result<Void> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        userService.updateUserInfo(userInfoDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 模擬用戶儲值（內部使用）
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/9/5 13:47:01
     */
    @PassToken
    @PostMapping("/balance/update")
    public Result<Void> updateBalance(@RequestBody UserBalanceDTO userBalanceDTO) {
        userBalanceService.update(userBalanceDTO);
        return SUCCESS;
    }
}
