package com.ht.tools.gateway.controller;

import com.ht.tools.common.core.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关健康检查控制器。
 * 用于提供网关自身存活探针，不依赖下游服务。
 */
@RestController
public class GatewayHealthController {

    /**
     * 网关健康检查接口：GET /api/health。
     * 返回简单文本 "OK"。
     *
     * @return 健康检查结果
     */
    @GetMapping("/api/health")
    public ApiResponse<Void> health() {
        return ApiResponse.ok();
    }
}
