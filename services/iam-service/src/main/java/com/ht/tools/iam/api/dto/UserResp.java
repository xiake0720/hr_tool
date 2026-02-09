package com.ht.tools.iam.api.dto;

import java.time.LocalDateTime;

/**
 * 用途：用户响应 DTO。
 * 职责/边界：仅用于 API 输出，不参与持久化。
 * 调用时机：用户相关接口返回时使用。
 * 线程模型：不可变 DTO，线程安全。
 */
public class UserResp {

    private final Long id;
    private final Long tenantId;
    private final String externalSub;
    private final String loginName;
    private final String username;
    private final String email;
    private final String phone;
    private final Boolean enabled;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * 行为：构造用户响应 DTO。
     *
     * @param id 主键 ID
     * @param tenantId 租户 ID
     * @param externalSub 外部身份标识
     * @param loginName 登录名
     * @param username 用户名
     * @param email 邮箱
     * @param phone 手机号
     * @param enabled 是否启用
     * @param createdAt 创建时间
     * @param updatedAt 更新时间
     */
    public UserResp(Long id,
                    Long tenantId,
                    String externalSub,
                    String loginName,
                    String username,
                    String email,
                    String phone,
                    Boolean enabled,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.tenantId = tenantId;
        this.externalSub = externalSub;
        this.loginName = loginName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * @return 主键 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return 租户 ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * @return 外部身份标识
     */
    public String getExternalSub() {
        return externalSub;
    }

    /**
     * @return 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return 是否启用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return 更新时间
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
