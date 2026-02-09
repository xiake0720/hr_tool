package com.ht.tools.iam.security;

import java.util.Set;

/**
 * 用途：保存当前用户的上下文信息，作为认证后的统一访问入口。
 * 职责/边界：仅承载请求级数据，不做持久化与业务逻辑。
 * 调用时机：由 CurrentUserContextFilter 构建并写入 ThreadLocal。
 * 线程模型：按请求创建与清理，避免跨线程复用。
 */
public class CurrentUserContext {

    private Long tenantId;
    private Long userId;
    private String externalSub;
    private String username;
    private String email;
    private Set<String> roles;
    private Set<String> permissions;
    private String requestId;

    /**
     * @return 租户 ID，可能为 null
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId 租户 ID
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return 用户 ID，可能为 null
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId 用户 ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return JWT 的 sub
     */
    public String getExternalSub() {
        return externalSub;
    }

    /**
     * @param externalSub JWT 的 sub
     */
    public void setExternalSub(String externalSub) {
        this.externalSub = externalSub;
    }

    /**
     * @return 用户名，可能为 null
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 邮箱，可能为 null
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 角色集合，可能为空
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @param roles 角色集合
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * @return 权限集合，可能为空
     */
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions 权限集合
     */
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return 请求追踪 ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId 请求追踪 ID
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
