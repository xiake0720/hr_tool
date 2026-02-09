package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用途：角色创建请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/roles 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class RoleCreateReq {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    /**
     * @return 角色编码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 角色编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }
}
