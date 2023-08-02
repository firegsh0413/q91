package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.security.AuthUser;
import com.icchance.q91.entity.security.SecureUser;
import com.icchance.q91.entity.vo.UserVO;
import com.icchance.q91.security.UserDetailImpl;
import com.icchance.q91.service.AuthUserService;
import com.icchance.q91.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AuthUserService authUserService;
    public UserDetailsServiceImpl(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserService.getAuthAccount(username);
        if (Objects.nonNull(username)) {
            log.info("account not found:" + username);
        }
        return new UserDetailImpl(authUser.getAccount(), authUser.getAddress(), new SimpleGrantedAuthority((authUser.getRole().toString())));
    }
}
