package com.ht.tools.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.iam.api.dto.UserCreateReq;
import com.ht.tools.iam.api.dto.UserResp;
import com.ht.tools.iam.api.dto.UserUpdateReq;
import com.ht.tools.iam.infrastructure.entity.UserEntity;
import com.ht.tools.iam.infrastructure.mapper.UserMapper;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import com.ht.tools.iam.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用途：用户管理服务实现。
 * 职责/边界：处理用户创建、更新、查询与禁用等业务规则。
 * 调用时机：由 Controller 或安全链路调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 行为：创建用户。
     *
     * @param req 创建请求
     * @return UserResp
     */
    @Override
    @Transactional
    public UserResp create(UserCreateReq req) {
        UserEntity entity = new UserEntity();
        entity.setTenantId(resolveTenantId());
        entity.setLoginName(req.getLoginName());
        entity.setPassword(req.getPassword());
        entity.setExternalSub(req.getExternalSub());
        entity.setUsername(req.getUsername());
        entity.setEmail(req.getEmail());
        entity.setPhone(req.getPhone());
        entity.setEnabled(Boolean.TRUE);
        try {
            userMapper.insert(entity);
        } catch (DuplicateKeyException ex) {
            throw new BizException(ErrorCode.IAM_5301);
        }
        return toResp(entity);
    }

    /**
     * 行为：更新用户。
     *
     * @param id 用户 ID
     * @param req 更新请求
     * @return UserResp
     */
    @Override
    @Transactional
    public UserResp update(Long id, UserUpdateReq req) {
        UserEntity entity = findById(id);
        entity.setExternalSub(req.getExternalSub());
        entity.setUsername(req.getUsername());
        entity.setEmail(req.getEmail());
        entity.setPhone(req.getPhone());
        if (req.getEnabled() != null) {
            entity.setEnabled(req.getEnabled());
        }
        userMapper.updateById(entity);
        return toResp(entity);
    }

    /**
     * 行为：获取用户详情。
     *
     * @param id 用户 ID
     * @return UserResp
     */
    @Override
    public UserResp get(Long id) {
        return toResp(findById(id));
    }

    /**
     * 行为：分页查询用户。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<UserResp>
     */
    @Override
    public PageResponse<UserResp> page(int page, int pageSize) {
        Long tenantId = resolveTenantId();
        int safePage = Math.max(page, 1);
        int safePageSize = Math.max(pageSize, 1);
        LambdaQueryWrapper<UserEntity> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.eq(UserEntity::getTenantId, tenantId);
        long total = userMapper.selectCount(countWrapper);

        int offset = (safePage - 1) * safePageSize;
        LambdaQueryWrapper<UserEntity> listWrapper = new LambdaQueryWrapper<>();
        listWrapper.eq(UserEntity::getTenantId, tenantId)
                .last("LIMIT " + offset + "," + safePageSize);
        List<UserResp> items = userMapper.selectList(listWrapper)
                .stream()
                .map(this::toResp)
                .collect(Collectors.toList());
        return new PageResponse<>(items, safePage, safePageSize, total);
    }

    /**
     * 行为：禁用用户。
     *
     * @param id 用户 ID
     */
    @Override
    @Transactional
    public void disable(Long id) {
        UserEntity entity = findById(id);
        entity.setEnabled(Boolean.FALSE);
        userMapper.updateById(entity);
    }

    /**
     * 行为：根据外部标识/登录名/邮箱查找用户。
     *
     * @param externalSub 外部标识
     * @param loginName 登录名
     * @param email 邮箱
     * @param tenantId 租户 ID
     * @return 用户实体
     */
    @Override
    public Optional<UserEntity> findByExternalSubOrLoginOrEmail(String externalSub,
                                                                String loginName,
                                                                String email,
                                                                Long tenantId) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getTenantId, tenantId);
        wrapper.and(query -> {
            boolean hasCond = false;
            if (StringUtils.hasText(externalSub)) {
                query.eq(UserEntity::getExternalSub, externalSub);
                hasCond = true;
            }
            if (StringUtils.hasText(loginName)) {
                if (hasCond) {
                    query.or();
                }
                query.eq(UserEntity::getLoginName, loginName);
                hasCond = true;
            }
            if (StringUtils.hasText(email)) {
                if (hasCond) {
                    query.or();
                }
                query.eq(UserEntity::getEmail, email);
            }
        });
        return Optional.ofNullable(userMapper.selectOne(wrapper));
    }

    private UserEntity findById(Long id) {
        Long tenantId = resolveTenantId();
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getId, id)
                .eq(UserEntity::getTenantId, tenantId);
        UserEntity entity = userMapper.selectOne(wrapper);
        if (entity == null) {
            throw new BizException(ErrorCode.SYS_1001);
        }
        return entity;
    }

    private Long resolveTenantId() {
        CurrentUserContext context = CurrentUserContextHolder.get();
        if (context != null && context.getTenantId() != null) {
            return context.getTenantId();
        }
        // TODO: Phase 1 使用固定 tenantId，占位后续接入真实租户解析。
        return FALLBACK_TENANT_ID;
    }

    private UserResp toResp(UserEntity entity) {
        return new UserResp(
                entity.getId(),
                entity.getTenantId(),
                entity.getExternalSub(),
                entity.getLoginName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getEnabled(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
