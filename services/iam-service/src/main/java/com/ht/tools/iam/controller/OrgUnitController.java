package com.ht.tools.iam.controller;

import com.ht.tools.common.core.api.ApiResponse;
import com.ht.tools.iam.api.dto.OrgCreateReq;
import com.ht.tools.iam.api.dto.OrgMoveReq;
import com.ht.tools.iam.api.dto.OrgTreeResp;
import com.ht.tools.iam.security.RequirePermission;
import com.ht.tools.iam.service.OrgUnitService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用途：组织架构管理 API。
 * 职责/边界：仅做参数校验与 DTO 组装，不直接访问 Mapper。
 * 调用时机：被 HTTP 请求调用。
 * 线程模型：无状态 Controller，线程安全。
 */
@RestController
@RequestMapping("/api/org-units")
public class OrgUnitController {

    private final OrgUnitService orgUnitService;

    public OrgUnitController(OrgUnitService orgUnitService) {
        this.orgUnitService = orgUnitService;
    }

    /**
     * 行为：创建组织节点。
     *
     * @param req 创建请求
     * @return ApiResponse<Long>
     */
    @PostMapping
    @RequirePermission("ORG_UNIT:MANAGE")
    public ApiResponse<Long> create(@Valid @RequestBody OrgCreateReq req) {
        return ApiResponse.ok(orgUnitService.create(req));
    }

    /**
     * 行为：查询组织树。
     *
     * @return ApiResponse<List<OrgTreeResp>>
     */
    @GetMapping("/tree")
    public ApiResponse<List<OrgTreeResp>> tree() {
        return ApiResponse.ok(orgUnitService.tree());
    }

    /**
     * 行为：移动组织节点。
     *
     * @param req 移动请求
     * @return ApiResponse<Void>
     */
    @PutMapping("/move")
    public ApiResponse<Void> move(@Valid @RequestBody OrgMoveReq req) {
        orgUnitService.move(req);
        return ApiResponse.ok();
    }
}
