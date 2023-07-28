package com.icchance.q91.dao;

import com.icchance.q91.entity.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Repository
public class FakeUserDB {

    private static Map<String, User> userDb = new TreeMap<>();

    public void create(User user) {
        synchronized (userDb) {
            userDb.put(user.getAccount(), user);
        }
        log.debug("userDb:{}", userDb);
    }

    public User get(String account) {
        return userDb.get(account);
    }

    public boolean isExist(String account) {
        return userDb.containsKey(account);
    }

    public User update(User user) {
        userDb.put(user.getAccount(), user);
        return userDb.get(user.getAccount());
    }
}
