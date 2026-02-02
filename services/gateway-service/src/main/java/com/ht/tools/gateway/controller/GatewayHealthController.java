package com.ht.tools.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：提供网关自身健康检查端点，区分网关与后端服务可用性。
 * 边界：仅用于网关自检，不代理下游服务。
 * 线程模型：无状态 Controller，线程安全。
 * 调用时机：运维或自动化探测时调用。
 * 清理责任：无。
 * 误用风险：不要在此接口返回敏感信息或后端状态细节。
 * 与其它组件关系：独立于路由规则，不依赖 iam-service。
 */
@RestController
public class GatewayHealthController {

    /**
     * 入参：无。
     * 返回：简单文本响应 "OK"。
     * 副作用：无。
     * 是否可空：返回值不为空。
     * 异常：无。
     * 线程安全：线程安全（无共享状态）。
     * 使用建议：仅用于网关自检，不作为业务 API 入口。
     *
     * @return 健康检查结果
     */
    @GetMapping("/gateway/health")
    public String health() {
        return "OK";
    }
}