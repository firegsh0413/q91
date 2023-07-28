package com.icchance.q91.common.constant;


import java.util.Arrays;

public enum UserAuthority {
    ADMIN, NORMAL;
    public UserAuthority fromString(String key) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }
}
