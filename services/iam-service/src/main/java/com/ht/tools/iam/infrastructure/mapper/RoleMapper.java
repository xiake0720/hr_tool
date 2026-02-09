package com.ht.tools.iam.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.tools.iam.infrastructure.entity.RoleEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用途：角色表 Mapper。
 * 职责/边界：仅负责 SQL 映射，不承载业务逻辑。
 * 调用时机：Service 层调用进行持久化操作。
 * 线程模型：Mapper 由 MyBatis 管理，线程安全由框架保证。
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 行为：按用户查询角色编码集合。
     *
     * @param tenantId 租户 ID
     * @param userId 用户 ID
     * @return 角色编码列表
     */
    @Select("""
            SELECT r.code
            FROM role r
            INNER JOIN user_role ur ON ur.role_id = r.id AND ur.tenant_id = r.tenant_id
            WHERE r.tenant_id = #{tenantId}
              AND ur.user_id = #{userId}
            """)
    List<String> selectRoleCodesByUserId(@Param("tenantId") Long tenantId, @Param("userId") Long userId);
}
