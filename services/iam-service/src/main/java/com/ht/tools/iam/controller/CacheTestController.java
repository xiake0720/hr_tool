package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.iam.api.dto.CacheTestResponse;
import com.ht.tools.iam.service.CacheTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：提供 Redis 缓存冒烟接口，验证缓存连通。
 * 职责/边界：仅负责调用 Service 并返回 DTO，不直接访问缓存。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 * 关系说明：依赖 CacheTestService 与 ApiResponse。
 */
@RestController
@RequestMapping("/api/cache")
public class CacheTestController {

    private final CacheTestService cacheTestService;

    public CacheTestController(CacheTestService cacheTestService) {
        this.cacheTestService = cacheTestService;
    }

    /**
     * 行为：写入测试缓存并读取返回。
     * 入参：无。
     * 出参：ApiResponse<CacheTestResponse>。
     * 异常：Redis 访问异常由全局异常处理器处理。
     *
     * @return ApiResponse<CacheTestResponse>
     */
    @GetMapping("/test")
    public ApiResponse<CacheTestResponse> testCache() {
        return ApiResponse.ok(cacheTestService.testCache());
    }
}
