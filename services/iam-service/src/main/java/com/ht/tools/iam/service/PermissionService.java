package com.ht.tools.iam.service;

import com.ht.tools.common.core.api.PageResponse;
import com.ht.tools.iam.api.dto.PermissionCreateReq;
import com.ht.tools.iam.api.dto.PermissionResp;

/**
 * 用途：权限管理服务入口。
 * 职责/边界：封装权限创建与分页查询。
 * 调用时机：由 Controller 调用。
 * 线程模型：无状态 Service，线程安全。
 */
public interface PermissionService {

    /**
     * 行为：创建权限。
     *
     * @param req 创建请求
     * @return PermissionResp
     */
    PermissionResp create(PermissionCreateReq req);

    /**
     * 行为：分页查询权限。
     *
     * @param page 页码
     * @param pageSize 每页大小
     * @return PageResponse<PermissionResp>
     */
    PageResponse<PermissionResp> page(int page, int pageSize);
}
