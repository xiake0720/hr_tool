package com.ht.tools.iam.service;

import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.BindRolePermissionsReq;
import com.ht.tools.iam.api.dto.RoleCreateReq;
import com.ht.tools.iam.api.dto.RoleResp;

/**
 * 用途：角色管理服务入口。
 * 职责/边界：封装角色创建、分页与权限绑定。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface RoleService {

    /**
     * 行为：创建角色。
     *
     * @param req 创建请求
     * @return RoleResp
     */
    RoleResp create(RoleCreateReq req);

    /**
     * 行为：分页查询角色。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<RoleResp>
     */
    PageResponse<RoleResp> page(int page, int pageSize);

    /**
     * 行为：为角色绑定权限。
     *
     * @param roleId 角色 ID
     * @param req 绑定请求
     */
    void bindPermissions(Long roleId, BindRolePermissionsReq req);
}
