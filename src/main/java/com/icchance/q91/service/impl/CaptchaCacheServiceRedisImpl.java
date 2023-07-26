package com.icchance.q91.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.icchance.q91.redis.RedisKit;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redis儲存類實做
 * </p>
 * @author 6687353
 * @since 2023/7/25 10:14:56
 */
@Service
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //private final RedisKit redisKit;

    //@Autowired(required = false)
/*    public CaptchaCacheServiceRedisImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        //this.redisKit = redisKit;
    }*/

    @Override
    public String type() {
        return "redis";
    }

    @Override
    public void set(String key, String value, long expireInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expireInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
