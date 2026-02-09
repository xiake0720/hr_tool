package com.ht.tools.iam.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.tools.iam.infrastructure.entity.PermissionEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用途：权限表 Mapper。
 * 职责/边界：仅负责 SQL 映射，不承载业务逻辑。
 * 调用时机：Service 层调用进行持久化操作。
 * 线程模型：Mapper 由 MyBatis 管理，线程安全由框架保证。
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 行为：按用户查询权限动作集合。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 权限动作列表
     */
    @Select("""
            SELECT DISTINCT p.action
            FROM permission p
            INNER JOIN role_permission rp ON rp.permission_id = p.id AND rp.tenant_id = p.tenant_id
            INNER JOIN user_role ur ON ur.role_id = rp.role_id AND ur.tenant_id = rp.tenant_id
            WHERE p.tenant_id = #{tenantId}
              AND ur.user_id = #{userId}
            """)
    List<String> selectActionsByUserId(@Param("tenantId") Long tenantId, @Param("userId") Long userId);
}
