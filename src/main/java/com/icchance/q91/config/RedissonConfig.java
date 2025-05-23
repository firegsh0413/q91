package com.icchance.q91.config;

import com.icchance.q91.util.RedissonLockUtil;
import com.icchance.q91.util.impl.RedissonDistributeLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <p>
 * Redisson設定類
 * </p>
 * @author 6687353
 * @since 2023/9/22 15:33:48
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonDistributeLocker redissonLocker(RedissonClient redissonClient) {
        RedissonDistributeLocker locker = new RedissonDistributeLocker(redissonClient);
        RedissonLockUtil.setLocker(locker);
        return locker;
    }

    // 服務關閉時會銷毀redisson線程池
    //@Bean(destroyMethod = "shutdown")
    @Bean
    public RedissonClient createRedissonClient() throws IOException {
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }

}
