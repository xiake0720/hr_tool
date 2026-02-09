package com.ht.tools.iam.service.impl;

import com.ht.tools.iam.api.dto.CacheTestResponse;
import com.ht.tools.iam.common.cache.CacheKeys;
import com.ht.tools.iam.service.CacheTestService;
import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 用途：Redis 冒烟缓存服务实现。
 * 职责/边界：仅用于写入和读取测试缓存，不承载业务逻辑。
 * 调用时机：由 CacheTestController 调用。
 * 线程模型：无状态 Service，线程安全。
 * 关系说明：依赖 StringRedisTemplate 与 CacheKeys。
 */
@Service
public class CacheTestServiceImpl implements CacheTestService {

    private static final Duration CACHE_TTL = Duration.ofSeconds(60);
    private static final String CACHE_VALUE = "pong";

    private final StringRedisTemplate stringRedisTemplate;

    public CacheTestServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 行为：写入固定 key/value 并读取返回，TTL 为 60s。
     * 入参：无。
     * 出参：CacheTestResponse。
     * 异常：Redis 访问异常由框架抛出并统一处理。
     *
     * @return CacheTestResponse
     */
    @Override
    public CacheTestResponse testCache() {
        stringRedisTemplate.opsForValue().set(CacheKeys.CACHE_TEST_PING, CACHE_VALUE, CACHE_TTL);
        String value = stringRedisTemplate.opsForValue().get(CacheKeys.CACHE_TEST_PING);
        return new CacheTestResponse(CacheKeys.CACHE_TEST_PING, value);
    }
}
