package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用途：权限创建请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/permissions 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class PermissionCreateReq {

    @NotBlank
    private String action;

    @NotBlank
    private String name;

    /**
     * @return 权限动作
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action 权限动作
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }
}
