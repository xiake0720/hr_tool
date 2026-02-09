package com.ht.tools.iam.api.dto;

import java.time.LocalDateTime;

/**
 * 用途：角色响应 DTO。
 * 职责/边界：仅用于 API 输出，不参与持久化。
 * 调用时机：角色相关接口返回时使用。
 * 线程模型：不可变 DTO，线程安全。
 */
public class RoleResp {

    private final Long id;
    private final String code;
    private final String name;
    private final LocalDateTime createdAt;

    /**
     * 行为：构造角色响应 DTO。
     *
     * @param id 角色 ID
     * @param code 角色编码
     * @param name 角色名称
     * @param createdAt 创建时间
     */
    public RoleResp(Long id, String code, String name, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.createdAt = createdAt;
    }

    /**
     * @return 角色 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return 角色编码
     */
    public String getCode() {
        return code;
    }

    /**
     * @return 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
