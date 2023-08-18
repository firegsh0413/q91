package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.security.AuthUser;

/**
 * <p>
 * 用戶認證服務類
 * </p>
 * @author 6687353
 * @since 2023/8/18 17:06:34
 */
public interface AuthUserService extends IService<User> {

    AuthUser getAuthAccount(String account);

    /**
     * <p>
     * 帳號取得用戶資訊
     * </p>
     * @param account 用戶帳號
     * @return com.icchance.q91.entity.model.User
     * @author 6687353
     * @since 2023/8/18 17:07:37
     */
    User getByAccount(String account);

    User getByAddress(String address);

    User getByAccountAndPassword(String account, String password);

    void createUser(User user);



}
