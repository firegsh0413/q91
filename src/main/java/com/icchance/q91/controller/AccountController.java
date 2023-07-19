package com.icchance.q91.controller;

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
@RestController
public class AccountController extends BaseController {

    @GetMapping("/register")
    public String register(@RequestParam String account, @RequestParam String username, @RequestParam String password) {
        return "";
    }
}
