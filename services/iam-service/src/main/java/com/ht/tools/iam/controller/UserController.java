package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.BindUserRolesReq;
import com.ht.tools.iam.api.dto.UserCreateReq;
import com.ht.tools.iam.api.dto.UserResp;
import com.ht.tools.iam.api.dto.UserUpdateReq;
import com.ht.tools.iam.service.UserRoleService;
import com.ht.tools.iam.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：用户管理 API。
 * 职责/边界：仅做参数校验与 DTO 组装，不直接访问 Mapper。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    /**
     * 行为：创建用户。
     *
     * @param req 创建请求
     * @return ApiResponse<UserResp>
     */
    @PostMapping
    public ApiResponse<UserResp> create(@Valid @RequestBody UserCreateReq req) {
        return ApiResponse.ok(userService.create(req));
    }

    /**
     * 行为：更新用户。
     *
     * @param id 用户 ID
     * @param req 更新请求
     * @return ApiResponse<UserResp>
     */
    @PutMapping("/{id}")
    public ApiResponse<UserResp> update(@PathVariable("id") Long id, @RequestBody UserUpdateReq req) {
        return ApiResponse.ok(userService.update(id, req));
    }

    /**
     * 行为：获取用户详情。
     *
     * @param id 用户 ID
     * @return ApiResponse<UserResp>
     */
    @GetMapping("/{id}")
    public ApiResponse<UserResp> get(@PathVariable("id") Long id) {
        return ApiResponse.ok(userService.get(id));
    }

    /**
     * 行为：分页查询用户。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return ApiResponse<PageResponse<UserResp>>
     */
    @GetMapping
    public ApiResponse<PageResponse<UserResp>> page(@RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        return ApiResponse.ok(userService.page(page, pageSize));
    }

    /**
     * 行为：禁用用户。
     *
     * @param id 用户 ID
     * @return ApiResponse<Void>
     */
    @PutMapping("/{id}/disable")
    public ApiResponse<Void> disable(@PathVariable("id") Long id) {
        userService.disable(id);
        return ApiResponse.ok();
    }

    /**
     * 行为：绑定用户角色。
     *
     * @param id 用户 ID
     * @param req 绑定请求
     * @return ApiResponse<Void>
     */
    @PostMapping("/{id}/roles")
    public ApiResponse<Void> bindRoles(@PathVariable("id") Long id, @Valid @RequestBody BindUserRolesReq req) {
        userRoleService.bindRoles(id, req);
        return ApiResponse.ok();
    }
}
