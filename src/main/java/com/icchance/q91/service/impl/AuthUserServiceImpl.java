package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.UserAuthority;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.security.AuthUser;
import com.icchance.q91.mapper.UserMapper;
import com.icchance.q91.service.AuthUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * <p>
 * 用戶認證服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/18 17:07:02
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<UserMapper, User> implements AuthUserService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 16;
    private static final Random RANDOM = new SecureRandom();

    @Override
    public AuthUser getAuthAccount(String account) {
        AuthUser authUser = new AuthUser();
/*        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        if (Objects.nonNull(user)) {
            BeanUtils.copyProperties(user, authUser);
        }
        List<UserAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(UserAuthority.NORMAL);
        authUser.setAuthorities(userAuthorities);*/
        return authUser;
    }

    /**
     * <p>
     * 帳號取得用戶資訊
     * </p>
     * @param account 用戶帳號
     * @return com.icchance.q91.entity.model.User
     * @author 6687353
     * @since 2023/8/18 17:07:37
     */
    @Override
    public User getByAccount(String account) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
    }

    @Override
    public User getByAddress(String address) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAddress, address));
    }

    @Override
    public User getByAccountAndPassword(String account, String password) {
        return this.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, account)
                .eq(User::getPassword, password));
    }

    @Override
    public void createUser(User user) {
        // 隨機生成錢包地址
        String address;
        boolean isDuplicate;
        do {
            address = generateRandomAddress();
            isDuplicate = Objects.nonNull(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAddress, address)));
        } while (isDuplicate);
        this.save(user);
    }

    private String generateRandomAddress() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
