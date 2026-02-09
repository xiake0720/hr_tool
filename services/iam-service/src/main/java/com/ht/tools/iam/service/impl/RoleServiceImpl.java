package com.ht.tools.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.iam.api.dto.BindRolePermissionsReq;
import com.ht.tools.iam.api.dto.RoleCreateReq;
import com.ht.tools.iam.api.dto.RoleResp;
import com.ht.tools.iam.infrastructure.entity.PermissionEntity;
import com.ht.tools.iam.infrastructure.entity.RoleEntity;
import com.ht.tools.iam.infrastructure.entity.RolePermissionEntity;
import com.ht.tools.iam.infrastructure.mapper.PermissionMapper;
import com.ht.tools.iam.infrastructure.mapper.RoleMapper;
import com.ht.tools.iam.infrastructure.mapper.RolePermissionMapper;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import com.ht.tools.iam.service.RoleService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用途：角色管理服务实现。
 * 职责/边界：处理角色创建、分页与权限绑定。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;

    public RoleServiceImpl(RoleMapper roleMapper,
                           PermissionMapper permissionMapper,
                           RolePermissionMapper rolePermissionMapper) {
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * 行为：创建角色。
     *
     * @param req 创建请求
     * @return RoleResp
     */
    @Override
    @Transactional
    public RoleResp create(RoleCreateReq req) {
        RoleEntity entity = new RoleEntity();
        entity.setTenantId(resolveTenantId());
        entity.setCode(req.getCode());
        entity.setName(req.getName());
        try {
            roleMapper.insert(entity);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.IAM_5301);
        }
        return new RoleResp(entity.getId(), entity.getCode(), entity.getName(), entity.getCreatedAt());
    }

    /**
     * 行为：分页查询角色。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<RoleResp>
     */
    @Override
    public PageResponse<RoleResp> page(int page, int pageSize) {
        Long tenantId = resolveTenantId();
        Page<RoleEntity> request = new Page<>(page, pageSize);
        LambdaQueryWrapper<RoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleEntity::getTenantId, tenantId);
        Page<RoleEntity> result = roleMapper.selectPage(request, wrapper);
        List<RoleResp> items = result.getRecords()
                .stream()
                .map(role -> new RoleResp(role.getId(), role.getCode(), role.getName(), role.getCreatedAt()))
                .collect(Collectors.toList());
        return new PageResponse<>(items, page, pageSize, result.getTotal());
    }

    /**
     * 行为：为角色绑定权限。
     *
     * @param roleId 角色 ID
     * @param req 绑定请求
     */
    @Override
    @Transactional
    public void bindPermissions(Long roleId, BindRolePermissionsReq req) {
        Long tenantId = resolveTenantId();
        RoleEntity role = findRole(roleId, tenantId);
        List<PermissionEntity> permissions = permissionMapper.selectBatchIds(req.getPermissionIds());
        if (permissions.size() != req.getPermissionIds().size()) {
            throw new BizException(ErrorCode.SYS_1001);
        }
        for (PermissionEntity permission : permissions) {
            if (!tenantId.equals(permission.getTenantId())) {
                throw new BizException(ErrorCode.SYS_1001);
            }
        }

        LambdaQueryWrapper<RolePermissionEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(RolePermissionEntity::getTenantId, tenantId)
                .eq(RolePermissionEntity::getRoleId, role.getId());
        rolePermissionMapper.delete(deleteWrapper);

        for (PermissionEntity permission : permissions) {
            RolePermissionEntity relation = new RolePermissionEntity();
            relation.setTenantId(tenantId);
            relation.setRoleId(role.getId());
            relation.setPermissionId(permission.getId());
            rolePermissionMapper.insert(relation);
        }
    }

    private RoleEntity findRole(Long id, Long tenantId) {
        LambdaQueryWrapper<RoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleEntity::getTenantId, tenantId)
                .eq(RoleEntity::getId, id);
        RoleEntity role = roleMapper.selectOne(wrapper);
        if (role == null) {
            throw new BizException(ErrorCode.SYS_1001);
        }
        return role;
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
