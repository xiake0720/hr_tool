package com.ht.tools.iam.service.impl;

import com.ht.tools.iam.common.cache.CacheKeys;
import com.ht.tools.iam.infrastructure.mapper.PermissionMapper;
import com.ht.tools.iam.infrastructure.mapper.RoleMapper;
import com.ht.tools.iam.service.UserPermissionService;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用途：用户权限加载服务实现，支持短 TTL 缓存。
 * 职责/边界：封装权限/角色查询与缓存读写。
 * 调用时机：安全过滤器构建 CurrentUserContext 时调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    private static final Duration CACHE_TTL = Duration.ofSeconds(60);

    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public UserPermissionServiceImpl(PermissionMapper permissionMapper,
                                     RoleMapper roleMapper,
                                     StringRedisTemplate stringRedisTemplate) {
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 行为：加载用户权限集合，优先从缓存读取。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 权限集合
     */
    @Override
    public Set<String> loadPermissions(Long tenantId, Long userId) {
        if (tenantId == null || userId == null) {
            return Collections.emptySet();
        }
        String cacheKey = CacheKeys.userPermissionsKey(userId);
        String cached = stringRedisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.hasText(cached)) {
            return splitToSet(cached);
        }
        List<String> actions = permissionMapper.selectActionsByUserId(tenantId, userId);
        Set<String> result = new HashSet<>(actions);
        stringRedisTemplate.opsForValue().set(cacheKey, String.join(",", result), CACHE_TTL);
        return result;
    }

    /**
     * 行为：加载用户角色集合，优先从缓存读取。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 角色集合
     */
    @Override
    public Set<String> loadRoles(Long tenantId, Long userId) {
        if (tenantId == null || userId == null) {
            return Collections.emptySet();
        }
        String cacheKey = CacheKeys.userRolesKey(userId);
        String cached = stringRedisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.hasText(cached)) {
            return splitToSet(cached);
        }
        List<String> roles = roleMapper.selectRoleCodesByUserId(tenantId, userId);
        Set<String> result = new HashSet<>(roles);
        stringRedisTemplate.opsForValue().set(cacheKey, String.join(",", result), CACHE_TTL);
        return result;
    }

    private Set<String> splitToSet(String cached) {
        return new HashSet<>(Arrays.asList(cached.split(",")));
    }
}
