package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：健康检查控制器，提供轻量探针接口。
 * 职责/边界：仅返回固定响应，不包含业务逻辑与数据库访问。
 * 调用时机：网关、监控或容器探针访问时调用。
 * 线程模型：无状态 Controller，线程安全。
 * 关系说明：统一使用 ApiResponse 输出并携带 requestId。
 */
@RestController
public class HealthController {

    /**
     * 行为：返回服务健康状态。
     * 入参：无。
     * 出参：ApiResponse<String>，固定返回 "OK"。
     * 异常：无。
     *
     * @return ApiResponse<String>
     */
    @GetMapping("/api/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("OK");
    }
}
