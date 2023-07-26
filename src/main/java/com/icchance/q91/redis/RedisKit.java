package com.icchance.q91.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisKit<K, V> {
    
    /**
     * <p>
     * 獲取RedisTemplate操作實例
     * </p>
     * @return org.springframework.data.redis.core.RedisTemplate
     * @author 6687353
     * @since 2023/7/25 14:54:46
     */
    RedisTemplate getRedisTemplate();
    
    /**
     * <p>
     * 注入緩存
     * </p>
     * @param k 緩存Key
     * @param v 緩存Value
     * @author 6687353
     * @since 2023/7/25 14:55:10
     */
    void set(K k, V v);

    /**
     * <p>
     * 注入緩存,並設置超時時間
     * </p>
     * @param k 緩存Key
     * @param v 緩存Value
     * @param timeout 超時時間
     * @author 6687353
     * @since 2023/7/25 14:55:29
     */
    void set(final K k, final V v, final Long timeout);

    /**
     * <p>
     * 注入緩存,並設置超時時間
     * </p>
     * @param k 緩存Key
     * @param v 緩存Value
     * @param timeout 超時時間,如果timeout為null或者time=0則不進行設置
     * @param unit 超時時間单位,如果unit為null,則默認為分鐘
     * @author 6687353
     * @since 2023/7/25 14:56:17
     */
    void set(final K k,final V v,final Long timeout,final TimeUnit unit);

    /**
     * <p>
     * 獲取緩存
     * </p>
     * @param k 緩存Key
     * @return V
     * @author 6687353
     * @since 2023/7/25 14:56:45
     */
    V get(final K k);

    /**
     * <p>
     * 是否存在Key
     * </p>
     * @param k 緩存Key
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:02
     */
    boolean hasKey(final K k);

    /**
     * <p>
     * 獲取多個Key的Value
     * </p>
     * @param keys Key列表
     * @return java.util.List<V>
     * @author 6687353
     * @since 2023/7/25 14:57:16
     */
    List<V> multiGet(final Set<K> keys);

    /**
     * <p>
     * 删除緩存
     * </p>
     * @param k 緩存Key
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:31
     */
    boolean delete(final K k);

    /**
     * <p>
     * 根据Key的前缀删除Key
     * </p>
     * @param p Key前缀
     * @return boolean
     * @author 6687353
     * @since 2023/7/25 14:57:44
     */
    boolean deleteByPrefix(String p);

    /**
     * <p>
     * 给Map添加一個元素
     * </p>
     * @param k 緩存Key
     * @param f Map中的Key
     * @param v 值
     * @author 6687353
     * @since 2023/7/25 14:58:02
     */
    void hSet(final K k, final String f, final V v);

    /**
     * <p>
     * 緩存一個Map
     * </p>
     * @param k 緩存Key
     * @param m 整個Map
     * @author 6687353
     * @since 2023/7/25 14:58:48
     */
    void hPutAll(final K k, final Map<K,V> m);

    /**
     * <p>
     * 獲取Map中的某個元素
     * </p>
     * @param k 緩存Key
     * @param f Map中的Key
     * @return V
     * @author 6687353
     * @since 2023/7/25 15:00:12
     */
    V hGet(final K k, final String f);

    /**
     * <p>
     * 獲取整個Map
     * </p>
     * @param k 緩存Key
     * @return java.util.Map
     * @author 6687353
     * @since 2023/7/25 15:01:05
     */
    Map entries(K k);

    /**
     * <p>
     * 獲取Map的元素個数
     * </p>
     * @param k 緩存Key
     * @return long
     * @author 6687353
     * @since 2023/7/25 15:02:26
     */
    long hSize(final K k);

    /**
     * <p>
     * 獲取多個Key列表
     * </p>
     * @param k Map Key
     * @param keys Key列表
     * @return java.util.List<V>
     * @author 6687353
     * @since 2023/7/25 15:02:47
     */
    List<V> multiGet(final K k, final List<K> keys);

    /**
     * <p>
     * 根據Key規則獲取所有key
     * </p>
     * @param pattern 規則
     * @return java.util.Set<K>
     * @author 6687353
     * @since 2023/7/25 15:03:50
     */
    Set<K> keys(K pattern);
}
