package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用途：角色绑定权限请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/roles/{id}/permissions 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class BindRolePermissionsReq {

    @NotEmpty
    private List<Long> permissionIds;

    /**
     * @return 权限 ID 列表
     */
    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    /**
     * @param permissionIds 权限 ID 列表
     */
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
