package com.ht.tools.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.iam.api.dto.PermissionCreateReq;
import com.ht.tools.iam.api.dto.PermissionResp;
import com.ht.tools.iam.infrastructure.entity.PermissionEntity;
import com.ht.tools.iam.infrastructure.mapper.PermissionMapper;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import com.ht.tools.iam.service.PermissionService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用途：权限管理服务实现。
 * 职责/边界：处理权限创建与分页查询。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 行为：创建权限。
     *
     * @param req 创建请求
     * @return PermissionResp
     */
    @Override
    @Transactional
    public PermissionResp create(PermissionCreateReq req) {
        PermissionEntity entity = new PermissionEntity();
        entity.setTenantId(resolveTenantId());
        entity.setAction(req.getAction());
        entity.setName(req.getName());
        try {
            permissionMapper.insert(entity);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.IAM_5301);
        }
        return new PermissionResp(entity.getId(), entity.getAction(), entity.getName(), entity.getCreatedAt());
    }

    /**
     * 行为：分页查询权限。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<PermissionResp>
     */
    @Override
    public PageResponse<PermissionResp> page(int page, int pageSize) {
        Long tenantId = resolveTenantId();
        Page<PermissionEntity> request = new Page<>(page, pageSize);
        LambdaQueryWrapper<PermissionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionEntity::getTenantId, tenantId);
        Page<PermissionEntity> result = permissionMapper.selectPage(request, wrapper);
        List<PermissionResp> items = result.getRecords()
                .stream()
                .map(permission -> new PermissionResp(permission.getId(), permission.getAction(),
                        permission.getName(), permission.getCreatedAt()))
                .collect(Collectors.toList());
        return new PageResponse<>(items, page, pageSize, result.getTotal());
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
