package com.ht.tools.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ht.tools.common.core.error.BizException;
import com.ht.tools.common.core.error.ErrorCode;
import com.ht.tools.iam.api.dto.OrgCreateReq;
import com.ht.tools.iam.api.dto.OrgMoveReq;
import com.ht.tools.iam.api.dto.OrgTreeResp;
import com.ht.tools.iam.infrastructure.entity.OrgUnitEntity;
import com.ht.tools.iam.infrastructure.mapper.OrgUnitMapper;
import com.ht.tools.iam.security.CurrentUserContext;
import com.ht.tools.iam.security.CurrentUserContextHolder;
import com.ht.tools.iam.service.OrgUnitService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用途：组织架构服务实现。
 * 职责/边界：处理组织树创建、移动与查询。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
@Service
public class OrgUnitServiceImpl implements OrgUnitService {

    private static final Long FALLBACK_TENANT_ID = 1L;

    private final OrgUnitMapper orgUnitMapper;

    public OrgUnitServiceImpl(OrgUnitMapper orgUnitMapper) {
        this.orgUnitMapper = orgUnitMapper;
    }

    /**
     * 行为：创建组织节点并维护 path。
     *
     * @param req 创建请求
     * @return 节点 ID
     */
    @Override
    @Transactional
    public Long create(OrgCreateReq req) {
        Long tenantId = resolveTenantId();
        OrgUnitEntity entity = new OrgUnitEntity();
        entity.setTenantId(tenantId);
        entity.setParentId(req.getParentId());
        entity.setName(req.getName());
        entity.setType(req.getType());
        entity.setPath("/");
        orgUnitMapper.insert(entity);

        String parentPath = "/";
        if (req.getParentId() != null) {
            OrgUnitEntity parent = findById(req.getParentId(), tenantId);
            parentPath = parent.getPath();
        }
        String path = parentPath + entity.getId() + "/";
        entity.setPath(path);
        orgUnitMapper.updateById(entity);
        return entity.getId();
    }

    /**
     * 行为：查询组织树（一次性拉取后组装）。
     *
     * @return 组织树列表
     */
    @Override
    public List<OrgTreeResp> tree() {
        Long tenantId = resolveTenantId();
        LambdaQueryWrapper<OrgUnitEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrgUnitEntity::getTenantId, tenantId);
        List<OrgUnitEntity> entities = orgUnitMapper.selectList(wrapper);
        Map<Long, OrgTreeResp> nodeMap = new HashMap<>();
        for (OrgUnitEntity entity : entities) {
            nodeMap.put(entity.getId(), toNode(entity));
        }
        List<OrgTreeResp> roots = new ArrayList<>();
        for (OrgUnitEntity entity : entities) {
            OrgTreeResp node = nodeMap.get(entity.getId());
            if (entity.getParentId() == null) {
                roots.add(node);
            } else {
                OrgTreeResp parent = nodeMap.get(entity.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        return roots;
    }

    /**
     * 行为：移动组织节点并维护 path，防止成环。
     *
     * @param req 移动请求
     */
    @Override
    @Transactional
    public void move(OrgMoveReq req) {
        Long tenantId = resolveTenantId();
        OrgUnitEntity current = findById(req.getId(), tenantId);
        if (req.getNewParentId() != null && req.getNewParentId().equals(req.getId())) {
            throw new BizException(ErrorCode.ORG_5201);
        }

        String newParentPath = "/";
        if (req.getNewParentId() != null) {
            OrgUnitEntity newParent = findById(req.getNewParentId(), tenantId);
            newParentPath = newParent.getPath();
            if (newParentPath.startsWith(current.getPath())) {
                throw new BizException(ErrorCode.ORG_5201);
            }
        }

        String oldPath = current.getPath();
        String newPath = newParentPath + current.getId() + "/";

        current.setParentId(req.getNewParentId());
        current.setPath(newPath);
        orgUnitMapper.updateById(current);

        LambdaQueryWrapper<OrgUnitEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrgUnitEntity::getTenantId, tenantId)
                .likeRight(OrgUnitEntity::getPath, oldPath);
        List<OrgUnitEntity> descendants = orgUnitMapper.selectList(wrapper);
        for (OrgUnitEntity descendant : descendants) {
            if (descendant.getId().equals(current.getId())) {
                continue;
            }
            String updatedPath = descendant.getPath().replaceFirst(Pattern.quote(oldPath), newPath);
            descendant.setPath(updatedPath);
            orgUnitMapper.updateById(descendant);
        }
    }

    private OrgUnitEntity findById(Long id, Long tenantId) {
        LambdaQueryWrapper<OrgUnitEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrgUnitEntity::getTenantId, tenantId)
                .eq(OrgUnitEntity::getId, id);
        OrgUnitEntity entity = orgUnitMapper.selectOne(wrapper);
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

    private OrgTreeResp toNode(OrgUnitEntity entity) {
        return new OrgTreeResp(entity.getId(), entity.getParentId(), entity.getName(), entity.getType(), entity.getPath());
    }
}
