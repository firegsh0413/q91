package com.icchance.q91.redis.impl;

import com.icchance.q91.common.constant.CacheConstants;
import com.icchance.q91.redis.RedisKit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisKitImpl<K, V>  implements RedisKit<K, V> {

    private RedisTemplate<K, V> redisTemplate;
    public RedisKitImpl(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * <p>
     * 獲取RedisTemplate操作實例
     * </p>
     *
     * @return org.springframework.data.redis.core.RedisTemplate
     * @author 6687353
     * @since 2023/7/25 14:54:46
     */
    @Override
    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * <p>
     * 注入緩存
     * </p>
     *
     * @param k 緩存Key
     * @param v 緩存Value
     * @author 6687353
     * @since 2023/7/25 14:55:10
     */
    @Override
    public void set(K k, V v) {
        this.set(k, v, CacheConstants.Default.TIME_OUT);
    }

    /**
     * <p>
     * 注入緩存,並設置超時時間
     * </p>
     *
     * @param k       緩存Key
     * @param v       緩存Value
     * @param timeout 超時時間
     * @author 6687353
     * @since 2023/7/25 14:55:29
     */
    @Override
    public void set(K k, V v, Long timeout) {
        this.set(k,v,timeout, CacheConstants.Default.TIME_UNIT);
    }

    /**
     * <p>
     * 注入緩存,並設置超時時間
     * </p>
     *
     * @param k       緩存Key
     * @param v       緩存Value
     * @param timeout 超時時間,如果timeout為null或者time=0則不進行設置
     * @param unit    超時時間单位,如果unit為null,則默認為分鐘
     * @author 6687353
     * @since 2023/7/25 14:56:17
     */
    @Override
    public void set(K k, V v, Long timeout, TimeUnit unit) {
        ValueOperations<K, V> vps = this.redisTemplate.opsForValue();
        // 是否設置超時時间
        if (null != timeout && timeout > 0L) {
            if (null == unit) {
                vps.set(k, v, timeout, TimeUnit.SECONDS);
            } else {
                vps.set(k, v, timeout, unit);
            }
        } else {
            vps.set(k, v);
        }
    }

    /**
     * <p>
     * 獲取緩存
     * </p>
     *
     * @param k 緩存Key
     * @return V
     * @author 6687353
     * @since 2023/7/25 14:56:45
     */
    @Override
    public V get(K k) {
        return this.redisTemplate.opsForValue().get(k);
    }

    /**
     * <p>
     * 是否存在Key
     * </p>
     *
     * @param k 緩存Key
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:02
     */
    @Override
    public boolean hasKey(K k) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(k));
    }

    /**
     * <p>
     * 獲取多個Key的Value
     * </p>
     *
     * @param keys Key列表
     * @return java.util.List<V>
     * @author 6687353
     * @since 2023/7/25 14:57:16
     */
    @Override
    public List<V> multiGet(Set<K> keys) {
        return this.redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * <p>
     * 删除緩存
     * </p>
     *
     * @param k 緩存Key
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:31
     */
    @Override
    public boolean delete(K k) {
        return Optional
                .ofNullable(this.redisTemplate.delete(k))
                .isPresent();
    }

    /**
     * <p>
     * 根据Key的前缀删除Key
     * </p>
     *
     * @param p Key前缀
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:44
     */
    @Override
    public boolean deleteByPrefix(String p) {
        RedisTemplate redisTemplate = this.redisTemplate;
        Set<K> keys = redisTemplate.keys(p + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return false;
        }
        return Optional
                .ofNullable(redisTemplate.delete(keys))
                .filter(d -> d.compareTo(0L) >= 1L)
                .isPresent();
    }

    /**
     * <p>
     * 给Map添加一個元素
     * </p>
     *
     * @param k 緩存Key
     * @param f Map中的Key
     * @param v 值
     * @author 6687353
     * @since 2023/7/25 14:58:02
     */
    @Override
    public void hSet(K k, String f, V v) {
        this.redisTemplate.opsForHash().put(k, f, v);
    }

    /**
     * <p>
     * 緩存一個Map
     * </p>
     *
     * @param k 緩存Key
     * @param m 整個Map
     * @author 6687353
     * @since 2023/7/25 14:58:48
     */
    @Override
    public void hPutAll(K k, Map<K, V> m) {
        this.redisTemplate.opsForHash().putAll(k, m);
    }

    /**
     * <p>
     * 獲取Map中的某個元素
     * </p>
     *
     * @param k 緩存Key
     * @param f Map中的Key
     * @return V
     * @author 6687353
     * @since 2023/7/25 15:00:12
     */
    @Override
    public V hGet(K k, String f) {
        return (V) this.redisTemplate.opsForHash().get(k, f);
    }

    /**
     * <p>
     * 獲取整個Map
     * </p>
     *
     * @param k 緩存Key
     * @return java.util.Map
     * @author 6687353
     * @since 2023/7/25 15:01:05
     */
    @Override
    public Map entries(K k) {
        return this.redisTemplate.opsForHash().entries(k);
    }

    /**
     * <p>
     * 獲取Map的元素個数
     * </p>
     *
     * @param k 緩存Key
     * @return long
     * @author 6687353
     * @since 2023/7/25 15:02:26
     */
    @Override
    public long hSize(K k) {
        return this.redisTemplate.opsForHash().size(k);
    }

    /**
     * <p>
     * 獲取多個Key列表
     * </p>
     *
     * @param k    Map Key
     * @param keys Key列表
     * @return java.util.List<V>
     * @author 6687353
     * @since 2023/7/25 15:02:47
     */
    @Override
    public List<V> multiGet(K k, List<K> keys) {
        return (List<V>) this.redisTemplate.opsForHash().multiGet(k, Collections.singleton(keys));
    }

    /**
     * <p>
     * 根據Key規則獲取所有key
     * </p>
     *
     * @param pattern 規則
     * @return java.util.Set<K>
     * @author 6687353
     * @since 2023/7/25 15:03:50
     */
    @Override
    public Set<K> keys(K pattern) {
        return this.redisTemplate.keys(pattern);
    }
}
