package com.icchance.q91.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redisson工具類
 * </p>
 * @author 6687353
 * @since 2023/9/22 15:36:45
 */
@Component
public class RedissonLockUtil {

    private static DistributedLocker locker;

    public static void setLocker(DistributedLocker locker) {
        RedissonLockUtil.locker = locker;
    }

    public static void lock(String lockKey) {
        locker.lock(lockKey);
    }

    public static void lock(String lockKey, int timeout) {
        locker.lock(lockKey, timeout);
    }

    public static void lock(String lockKey, int timeout, TimeUnit unit) {
        locker.lock(lockKey, unit, timeout);
    }

    public static void unlock(String lockKey) {
        locker.unlock(lockKey);
    }

    public static boolean tryLock(String lockKey) {
        return locker.tryLock(lockKey);
    }

    public static boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        return locker.tryLock(lockKey, unit, waitTime, leaseTime);
    }

    public static boolean isLocked(String lockKey) {
        return locker.isLocked(lockKey);
    }

    public static boolean isHeldByCurrentThread(String lockKey) {
        return locker.isHeldByCurrentThread(lockKey);
    }
}
