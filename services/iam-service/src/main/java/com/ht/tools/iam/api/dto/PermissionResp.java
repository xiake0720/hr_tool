package com.ht.tools.iam.api.dto;

import java.time.LocalDateTime;

/**
 * 用途：权限响应 DTO。
 * 职责/边界：仅用于 API 输出，不参与持久化。
 * 调用时机：权限相关接口返回时使用。
 * 线程模型：不可变 DTO，线程安全。
 */
public class PermissionResp {

    private final Long id;
    private final String action;
    private final String name;
    private final LocalDateTime createdAt;

    /**
     * 行为：构造权限响应 DTO。
     *
     * @param id 权限 ID
     * @param action 权限动作
     * @param name 权限名称
     * @param createdAt 创建时间
     */
    public PermissionResp(Long id, String action, String name, LocalDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.name = name;
        this.createdAt = createdAt;
    }

    /**
     * @return 权限 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return 权限动作
     */
    public String getAction() {
        return action;
    }

    /**
     * @return 权限名称
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
