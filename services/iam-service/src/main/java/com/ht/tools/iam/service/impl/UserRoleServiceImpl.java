package com.ht.tools.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.iam.api.dto.BindUserRolesReq;
import com.ht.tools.iam.infrastructure.entity.RoleEntity;
import com.ht.tools.iam.infrastructure.entity.UserEntity;
import com.ht.tools.iam.infrastructure.entity.UserRoleEntity;
import com.ht.tools.iam.infrastructure.mapper.RoleMapper;
import com.ht.tools.iam.infrastructure.mapper.UserMapper;
import com.ht.tools.iam.infrastructure.mapper.UserRoleMapper;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import com.ht.tools.iam.service.UserRoleService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用途：用户角色绑定服务实现。
 * 职责/边界：维护 user_role 关系。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserMapper userMapper,
                               RoleMapper roleMapper,
                               UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 行为：为用户绑定角色（覆盖式绑定）。
     *
     * @param userId 用户 ID
     * @param req 绑定请求
     */
    @Override
    @Transactional
    public void bindRoles(Long userId, BindUserRolesReq req) {
        Long tenantId = resolveTenantId();
        UserEntity user = findUser(userId, tenantId);
        List<RoleEntity> roles = roleMapper.selectBatchIds(req.getRoleIds());
        if (roles.size() != req.getRoleIds().size()) {
            throw new BizException(ErrorCode.SYS_1001);
        }
        for (RoleEntity role : roles) {
            if (!tenantId.equals(role.getTenantId())) {
                throw new BizException(ErrorCode.SYS_1001);
            }
        }

        LambdaQueryWrapper<UserRoleEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(UserRoleEntity::getTenantId, tenantId)
                .eq(UserRoleEntity::getUserId, user.getId());
        userRoleMapper.delete(deleteWrapper);

        for (RoleEntity role : roles) {
            UserRoleEntity relation = new UserRoleEntity();
            relation.setTenantId(tenantId);
            relation.setUserId(user.getId());
            relation.setRoleId(role.getId());
            userRoleMapper.insert(relation);
        }
    }

    private UserEntity findUser(Long id, Long tenantId) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getTenantId, tenantId)
                .eq(UserEntity::getId, id);
        UserEntity user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BizException(ErrorCode.SYS_1001);
        }
        return user;
    }

    private Long resolveTenantId() {
        CurrentUserContext context = CurrentUserContextHolder.get();
        if (context != null && context.getTenantId() != null) {
            return context.getTenantId();
        }
        // TODO: Phase 1 使用固定 tenantId，占位后续接入真实租户解析。
        return FALLBACK_TENANT_ID;
    }
}
