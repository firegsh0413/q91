package com.icchance.q91.dao;

import com.icchance.q91.entity.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Slf4j
@Repository
public class FakeUserDB {

    private static Map<Integer, User> userDb = new TreeMap<>();

    public void create(User user) {
        synchronized (userDb) {
            userDb.put(1, user);
        }
        log.debug("userDb:{}", userDb);
    }

    public User get(String account) {
        Optional<User> any = userDb.values().stream().filter(v -> account.equals(v.getAccount())).findAny();
        return any.orElse(null);
    }

    public boolean isExist(String account) {
        return userDb.values().stream().anyMatch(v -> account.equals(v.getAccount()));
    }

    public User update(User user) {
        userDb.put(user.getId(), user);
        return userDb.get(user.getId());
    }
}
