package com.ht.tools.iam.service;

import java.util.Set;

/**
 * 用途：加载用户角色与权限集合。
 * 职责/边界：封装权限加载与缓存逻辑。
 * 调用时机：安全过滤器构建 CurrentUserContext 时调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface UserPermissionService {

    /**
     * 行为：加载用户权限集合。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 权限集合
     */
    Set<String> loadPermissions(Long tenantId, Long userId);

    /**
     * 行为：加载用户角色集合。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 角色集合
     */
    Set<String> loadRoles(Long tenantId, Long userId);
}
