package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.BindRolePermissionsReq;
import com.ht.tools.iam.api.dto.RoleCreateReq;
import com.ht.tools.iam.api.dto.RoleResp;
import com.ht.tools.iam.security.RequirePermission;
import com.ht.tools.iam.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：角色管理 API。
 * 职责/边界：仅做参数校验与 DTO 组装，不直接访问 Mapper。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 行为：创建角色。
     *
     * @param req 创建请求
     * @return ApiResponse<RoleResp>
     */
    @PostMapping
    @RequirePermission("ROLE:MANAGE")
    public ApiResponse<RoleResp> create(@Valid @RequestBody RoleCreateReq req) {
        return ApiResponse.ok(roleService.create(req));
    }

    /**
     * 行为：分页查询角色。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return ApiResponse<PageResponse<RoleResp>>
     */
    @GetMapping
    public ApiResponse<PageResponse<RoleResp>> page(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "20") int pageSize) {
        return ApiResponse.ok(roleService.page(page, pageSize));
    }

    /**
     * 行为：绑定角色权限。
     *
     * @param id 角色 ID
     * @param req 绑定请求
     * @return ApiResponse<Void>
     */
    @PostMapping("/{id}/permissions")
    public ApiResponse<Void> bindPermissions(@PathVariable("id") Long id,
                                             @Valid @RequestBody BindRolePermissionsReq req) {
        roleService.bindPermissions(id, req);
        return ApiResponse.ok();
    }
}
