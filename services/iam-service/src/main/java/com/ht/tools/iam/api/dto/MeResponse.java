package com.ht.tools.iam.api.dto;

import java.util.Set;

/**
 * 用途：/api/me 接口响应 DTO，返回当前用户的最小上下文信息。
 * 职责/边界：仅用于 API 输出，不参与持久化与业务逻辑。
 * 调用时机：MeController 返回 ApiResponse 时使用。
 * 线程模型：不可变 DTO，线程安全。
 */
public class MeResponse {

    private final Long tenantId;
    private final Long userId;
    private final String externalSub;
    private final String username;
    private final String email;
    private final Set<String> roles;
    private final Set<String> permissions;
    private final String requestId;

    /**
     * 行为：构造 /api/me 响应 DTO。
     * 入参：tenantId/userId 允许为空，roles/permissions 允许空集合。
     * 出参：MeResponse。
     * 异常：无。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @param externalSub JWT sub
     * @param username 用户名
     * @param email 邮箱
     * @param roles 角色集合
     * @param permissions 权限集合
     * @param requestId 请求追踪 ID
     */
    public MeResponse(Long tenantId,
                      Long userId,
                      String externalSub,
                      String username,
                      String email,
                      Set<String> roles,
                      Set<String> permissions,
                      String requestId) {
        this.tenantId = tenantId;
        this.userId = userId;
        this.externalSub = externalSub;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.permissions = permissions;
        this.requestId = requestId;
    }

    /**
     * @return 租户 ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * @return 用户 ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @return JWT sub
     */
    public String getExternalSub() {
        return externalSub;
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
     * @return 角色集合
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @return 权限集合
     */
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * @return 请求追踪 ID
     */
    public String getRequestId() {
        return requestId;
    }
}
