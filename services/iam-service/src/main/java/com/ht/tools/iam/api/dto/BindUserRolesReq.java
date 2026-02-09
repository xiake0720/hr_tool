package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用途：用户绑定角色请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/users/{id}/roles 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class BindUserRolesReq {

    @NotEmpty
    private List<Long> roleIds;

    /**
     * @return 角色 ID 列表
     */
    public List<Long> getRoleIds() {
        return roleIds;
    }

    /**
     * @param roleIds 角色 ID 列表
     */
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
