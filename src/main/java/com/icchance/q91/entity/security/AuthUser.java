package com.icchance.q91.entity.security;

import com.icchance.q91.common.constant.UserAuthority;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.UserVO;
import lombok.Data;

import java.util.List;

@Data
public class AuthUser extends User {

    private List<UserAuthority> authorities;
}
