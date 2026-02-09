package com.ht.tools.iam.service;

import com.ht.tools.iam.api.dto.BindUserRolesReq;

/**
 * 用途：用户角色绑定服务入口。
 * 职责/边界：封装用户与角色关系维护。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface UserRoleService {

    /**
     * 行为：为用户绑定角色。
     *
     * @param userId 用户 ID
     * @param req 绑定请求
     */
    void bindRoles(Long userId, BindUserRolesReq req);
}
