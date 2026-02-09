package com.ht.tools.iam.common.cache;

/**
 * 用途：统一 Redis 缓存 key 规范，避免散乱命名与冲突。
 * 职责/边界：仅提供 key 生成规则与常量，不包含缓存读写逻辑。
 * 调用时机：Service/Repository 进行缓存访问前调用。
 * 线程模型：纯工具类，无状态，线程安全。
 * 关系说明：与 RedisTemplate/缓存组件配合使用。
 */
public final class CacheKeys {

    /**
     * 统一 key 前缀。
     */
    public static final String PREFIX = "hr:iam";

    /**
     * infra 冒烟缓存 key。
     */
    public static final String CACHE_TEST_PING = PREFIX + ":cache-test:ping";

    /**
     * 用户权限缓存 key 前缀。
     */
    public static final String USER_PERMISSIONS_PREFIX = PREFIX + ":perm:user:";

    /**
     * 用户角色缓存 key 前缀。
     */
    public static final String USER_ROLES_PREFIX = PREFIX + ":role:user:";

    /**
     * 行为：生成用户权限缓存 key。
     *
     * @param userId 用户 ID
     * @return 缓存 key
     */
    public static String userPermissionsKey(Long userId) {
        return USER_PERMISSIONS_PREFIX + userId;
    }

    /**
     * 行为：生成用户角色缓存 key。
     *
     * @param userId 用户 ID
     * @return 缓存 key
     */
    public static String userRolesKey(Long userId) {
        return USER_ROLES_PREFIX + userId;
    }

    private CacheKeys() {
    }
}
