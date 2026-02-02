package com.ht.tools.iam.controller;

import com.ht.tools.iam.common.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：提供 IAM 服务的基础健康检查端点。
 * 边界：仅用于可用性探测，不承载业务数据。
 * 线程模型：无状态 Controller，线程安全。
 * 调用时机：运维或网关探测时调用。
 * 清理责任：无。
 * 误用风险：不要在此接口返回敏感信息。
 * 与其它组件关系：响应体使用 {@link ApiResponse} 包装。
 */
@RestController
public class HealthController {

    /**
     * 入参：无。
     * 返回：统一成功响应（data 为 "OK"）。
     * 副作用：会在响应中携带 requestId。
     * 是否可空：返回值不为空。
     * 异常：无。
     * 线程安全：线程安全（无共享状态）。
     * 使用建议：仅用于健康探测，避免作为业务接口依赖。
     *
     * @return ApiResponse<String>
     */
    @GetMapping("/api/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("OK");
    }
}