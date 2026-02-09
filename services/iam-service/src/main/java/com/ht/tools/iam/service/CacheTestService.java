package com.ht.tools.iam.service;

import com.ht.tools.iam.api.dto.CacheTestResponse;

/**
 * 用途：提供 Redis 冒烟缓存服务入口。
 * 职责/边界：封装缓存读写流程，不暴露缓存实现细节给 Controller。
 * 调用时机：由 CacheTestController 调用。
 * 线程模型：无状态 Service，线程安全。
 * 关系说明：依赖 RedisTemplate，返回 CacheTestResponse DTO。
 */
public interface CacheTestService {

    /**
     * 行为：写入测试缓存并读取返回，用于验证 Redis 连通。
     * 入参：无。
     * 出参：CacheTestResponse。
     * 异常：Redis 不可用时由框架抛出异常并统一处理。
     *
     * @return CacheTestResponse
     */
    CacheTestResponse testCache();
}
