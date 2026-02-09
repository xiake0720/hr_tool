package com.ht.tools.iam.service;

import com.ht.tools.iam.api.dto.OrgCreateReq;
import com.ht.tools.iam.api.dto.OrgMoveReq;
import com.ht.tools.iam.api.dto.OrgTreeResp;
import java.util.List;

/**
 * 用途：组织架构服务入口。
 * 职责/边界：封装组织树的创建、移动与查询。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface OrgUnitService {

    /**
     * 行为：创建组织节点。
     *
     * @param req 创建请求
     * @return 节点 ID
     */
    Long create(OrgCreateReq req);

    /**
     * 行为：查询组织树。
     *
     * @return 组织树列表
     */
    List<OrgTreeResp> tree();

    /**
     * 行为：移动组织节点。
     *
     * @param req 移动请求
     */
    void move(OrgMoveReq req);
}
