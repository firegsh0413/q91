package com.icchance.q91.util;

import lombok.NonNull;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>
 * 分佈式鎖介面
 * </p>
 * @author 6687353
 * @since 2023/9/22 15:37:31
 */
public interface DistributedLocker {

    /**
     * <p>
     * 加鎖
     * </p>
     * @param lockKey key
     * @return org.redisson.api.RLock
     * @author 6687353
     * @since 2023/9/20 17:50:48
     */
    RLock lock(String lockKey);

    /**
     * <p>
     * 加鎖
     * </p>
     * @param lockKey key
     * @param timeout 有效時間
     * @return org.redisson.api.RLock
     * @author 6687353
     * @since 2023/9/20 17:50:59
     */
    RLock lock(String lockKey, long timeout);

    /**
     * <p>
     * 加鎖
     * </p>
     * @param lockKey key
     * @param unit 時間單位
     * @param timeout  有效時間
     * @return org.redisson.api.RLock
     * @author 6687353
     * @since 2023/9/20 17:51:19
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     * <p>
     * 尝试获取锁
     * </p>
     * @param lockKey  key
     * @return boolean
     * @author 6687353
     * @since 2023/9/22 15:40:12
     */
    boolean tryLock(String lockKey);

    /**
     * <p>
     * 尝试获取锁，获取到则持有该锁leaseTime时间.
     * </p>
     * @param lockKey key
     * @param unit 時間單位
     * @param leaseTime 鎖持有時間
     * @return boolean
     * @author 6687353
     * @since 2023/9/20 17:54:15
     */
    boolean tryLock(String lockKey, TimeUnit unit, long leaseTime);

    /**
     * <p>
     * 尝试获取锁，获取到则持有该锁leaseTime时间.
     * 若未获取到，在waitTime时间内一直尝试获取，超过waitTime还未获取到则返回false
     * </p>
     * @param lockKey key
     * @param unit 時間單位
     * @param waitTime 嘗試獲取時間
     * @param leaseTime 鎖持有時間
     * @return boolean
     * @author 6687353
     * @since 2023/9/20 17:54:56
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * <p>
     * 解鎖
     * </p>
     * @param lockKey  key
     * @author 6687353
     * @since 2023/9/22 15:43:21
     */
    void unlock(String lockKey);

    /**
     * <p>
     * 解鎖
     * </p>
     * @param lock 鎖對象
     * @author 6687353
     * @since 2023/9/22 15:43:21
     */
    void unlock(RLock lock);

    /**
     * <p>
     * 鎖是否被線程持有
     * </p>
     * @param lockKey key
     * @return boolean
     * @author 6687353
     * @since 2023/9/22 15:43:54
     */
    boolean isLocked(String lockKey);

    /**
     * <p>
     * 是否被當前線程鎖定
     * </p>
     * @param lockKey  key
     * @return boolean
     * @author 6687353
     * @since 2023/9/22 15:44:48
     */
    boolean isHeldByCurrentThread(String lockKey);

    <T> T tryLockAndRun(@NonNull String lockKey, @NonNull TimeUnit unit, long waitTime, long leaseTime, @NonNull Supplier<T> supplier, String scene);
}
