package com.icchance.q91.util.impl;

import cn.hutool.core.date.SystemClock;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.util.DistributedLocker;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * Redisson分佈式鎖實現
 * </p>
 * @author 6687353
 * @since 2023/9/22 15:37:04
 */
@Slf4j
@Component
public class RedissonDistributeLocker implements DistributedLocker {

    private RedissonClient redissonClient;

    public RedissonDistributeLocker (RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock lock(String lockKey) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long leaseTime) {
        RLock lock = this.redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0L, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = this.redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = this.redissonClient.getLock(lockKey);
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException e) {

        }
    }

    @Override
    public void unlock(RLock lock) {
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException e) {

        }
    }

    @Override
    public boolean isLocked(String lockKey) {
        return redissonClient.getLock(lockKey).isLocked();
    }

    @Override
    public boolean isHeldByCurrentThread(String lockKey) {
        return redissonClient.getLock(lockKey).isHeldByCurrentThread();
    }

    @Override
    public <T> T tryLockAndRun(@NonNull String lockKey, @NonNull TimeUnit unit, long waitTime, long leaseTime, @NonNull Supplier<T> supplier, String scene) {
        final long start = SystemClock.now();
        final boolean tryLock = this.tryLock(lockKey, unit, waitTime, leaseTime);
        final long end = SystemClock.now();
        if (!tryLock) {
            log.error("[{}]獲取分布式鎖失敗, lockKey = {}, 耗時{}ms", scene, lockKey, end - start);
            throw new ServiceException(ResultCode.TRY_REDIS_LOCK_FAILED);
        }
        try {
            log.info("[{}]獲取分布式鎖成功, lockKey = {}, 耗時{}ms", scene, lockKey, end - start);
            return supplier.get();
        } finally {
            this.unlock(lockKey);
        }
    }
}
