package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.PermissionCreateReq;
import com.ht.tools.iam.api.dto.PermissionResp;
import com.ht.tools.iam.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：权限管理 API。
 * 职责/边界：仅做参数校验与 DTO 组装，不直接访问 Mapper。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 */
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 行为：创建权限。
     *
     * @param req 创建请求
     * @return ApiResponse<PermissionResp>
     */
    @PostMapping
    public ApiResponse<PermissionResp> create(@Valid @RequestBody PermissionCreateReq req) {
        return ApiResponse.ok(permissionService.create(req));
    }

    /**
     * 行为：分页查询权限。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return ApiResponse<PageResponse<PermissionResp>>
     */
    @GetMapping
    public ApiResponse<PageResponse<PermissionResp>> page(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "20") int pageSize) {
        return ApiResponse.ok(permissionService.page(page, pageSize));
    }
}
