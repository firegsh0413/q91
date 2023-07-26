package com.icchance.q91.controller;

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
     * 取得驗證碼
     * </p>
     * @param account 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 15:26:23
     */
    @GetMapping("/captcha")
    public Result getCaptcha(@RequestParam String account) {
        return userService.getCaptcha(account);
    }

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
     * @since 2023/7/21 10:05:17
     */
    @PostMapping("/register")
    public Result register(@RequestParam String account, @RequestParam String username, @RequestParam String password,
                           @RequestParam String chkPassword, @RequestParam String fundPassword, @RequestParam String chkFundPassword) {
        return userService.register(account, username, password, chkPassword, fundPassword, chkFundPassword);
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
    @PostMapping("/logout")
    public Result logout(@RequestParam String token) {
        return userService.logout(token);
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
    @GetMapping("/info")
    public Result getUserInfo(@RequestParam String token) {
        return userService.getUserInfo(token);
    }

    @GetMapping("/balance")
    public Result getBalance(@RequestParam String token) {
        return null;
    }

    @GetMapping("/transaction")
    public Result getTransaction(@RequestParam String token) {
        return null;
    }

    @PostMapping("/certificate")
    public Result certificate(@RequestParam String token, @RequestParam String name, @RequestParam String idNumber,
                              @RequestParam String idCard, @RequestParam String facePhoto) {
        return null;
    }
}
