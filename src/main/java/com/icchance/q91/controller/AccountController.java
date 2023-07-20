package com.icchance.q91.controller;

import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.AccountService;
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
@RequestMapping(AccountController.PREFIX)
@RestController
public class AccountController extends BaseController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    static final String PREFIX = "/account";

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
        return Result.builder()
                .resultCode(SUCCESS.getResultCode())
                .resultMap(accountService.getCaptcha(account))
                .msg(SUCCESS.getMsg()).build();
    }

    @PostMapping("/register")
    public Result register(@RequestParam String account, @RequestParam String username, @RequestParam String password,
                           @RequestParam String chkPassword, @RequestParam String fundPassword, @RequestParam String chkFundPassword) {
        return Result.builder()
                .resultCode(SUCCESS.getResultCode())
                .resultMap(accountService.register(account, username, password, chkPassword, fundPassword, chkFundPassword))
                .msg(SUCCESS.getMsg()).build();
    }
}
