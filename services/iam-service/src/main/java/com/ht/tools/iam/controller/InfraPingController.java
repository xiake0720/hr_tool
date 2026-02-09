package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.iam.api.dto.InfraPingResponse;
import com.ht.tools.iam.service.InfraPingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：提供 infra 冒烟 API，验证 MyBatis+MySQL 读通。
 * 职责/边界：只做参数校验与 DTO 组装，不直接访问 Mapper。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 * 关系说明：依赖 InfraPingService 与 ApiResponse。
 */
@RestController
@RequestMapping("/api/infra")
public class InfraPingController {

    private final InfraPingService infraPingService;

    public InfraPingController(InfraPingService infraPingService) {
        this.infraPingService = infraPingService;
    }

    /**
     * 行为：返回 infra 冒烟结果。
     * 入参：无。
     * 出参：ApiResponse<InfraPingResponse>。
     * 异常：数据库或服务异常由全局异常处理器处理。
     *
     * @return ApiResponse<InfraPingResponse>
     */
    @GetMapping("/ping")
    public ApiResponse<InfraPingResponse> ping() {
        return ApiResponse.ok(infraPingService.getPing());
    }
}
