package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.security.AuthUser;

public interface AuthUserService extends IService<User> {

    AuthUser getAuthAccount(String account);

    User getByAccount(String account);

    User getByAddress(String address);

    User getByAccountAndPassword(String account, String password);

    void createUser(User user);



}
