package com.icchance.q91.controller;

import com.icchance.q91.annotation.PassToken;
import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    static final String PREFIX = "/user";

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
     * @since 2023/7/21 10:05:17
     */
    @PassToken
    @PostMapping("/register")
    public Result register(@RequestParam String account, @RequestParam String username, @RequestParam String password, @RequestParam String fundPassword) {
        return userService.register(account, username, password, fundPassword);
    }

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
     * @since 2023/7/21 10:09:52
     */
    @PassToken
    @PostMapping("/login")
    public Result login(@RequestParam String account, @RequestParam String password, @RequestParam String cId, @RequestParam String captcha) {
        return userService.login(account, password, cId, captcha);
    }

    /**
     * <p>
     * 登出
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 12:00:18
     */
    @PassToken
    @PostMapping("/logout")
    public Result logout(@RequestParam String token) {
        userService.logout(token);
        return SUCCESS;
    }

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    @UserLoginToken
    @GetMapping("/info")
    public Result getUserInfo(@RequestParam String token) {
        return userService.getUserInfo(token);
    }

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 09:47:52
     */
    @UserLoginToken
    @GetMapping("/balance")
    public Result getBalance(@RequestParam String token) {
        return userService.getBalance(token);
    }

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
     * @since 2023/8/14 09:56:33
     */
    @UserLoginToken
    @PostMapping("/certificate")
    public Result certificate(@RequestParam String token, @RequestParam String name, @RequestParam String idNumber,
                              @RequestParam String idCard, @RequestParam String facePhoto) {
        return userService.certificate(token, name, idNumber, idCard, facePhoto);
    }

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param token 令牌
     * @param oldPassword 原密碼
     * @param newPassword 新密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:48:26
     */
    @UserLoginToken
    @PostMapping("/pwd/update")
    public Result updatePassword(@RequestParam String token, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.updatePassword(token, oldPassword, newPassword);
    }

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param token 令牌
     * @param oldFundPassword 原交易密碼
     * @param newFundPassword 新交易密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:00:04
     */
    @UserLoginToken
    @PostMapping("/fundPwd/update")
    public Result updateFundPassword(@RequestParam String token, @RequestParam String oldFundPassword, @RequestParam String newFundPassword) {
        return userService.updateFundPassword(token, oldFundPassword, newFundPassword);
    }

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param token 令牌
     * @param username 用戶名稱
     * @param avatar 用戶頭像
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:03:37
     */
    @UserLoginToken
    @PostMapping("/info/update")
    public Result updateUserInfo(@RequestParam String token, @RequestParam String username, @RequestParam String avatar) {
        return userService.updateUserInfo(token, username, avatar);
    }
}
