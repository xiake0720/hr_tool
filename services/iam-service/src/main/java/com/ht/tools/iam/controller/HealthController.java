package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查控制器。
 * 用于提供服务存活/就绪探针的轻量接口，仅返回固定响应，不包含业务逻辑。
 * 主要供网关、监控与容器探针调用，线程安全（无共享可变状态）。
 * 关联统一返回 {@link ApiResponse}；避免在此接口做鉴权或访问数据库，确保探针稳定。
 */
@RestController
public class HealthController {

    /**
     * 健康检查接口：GET /api/health。
     * 返回 {@code "OK"}，响应体由 {@link ApiResponse} 包装，含 requestId（由请求上下文生成）。
     * 无参数、无副作用，线程安全。
     *
     * @return ApiResponse<String>
     */
    @GetMapping("/api/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("OK");
    }
}
