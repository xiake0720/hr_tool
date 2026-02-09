package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 用途：组织节点移动请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：PUT /api/org-units/move 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class OrgMoveReq {

    @NotNull
    private Long id;

    private Long newParentId;

    /**
     * @return 节点 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 节点 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 新父节点 ID
     */
    public Long getNewParentId() {
        return newParentId;
    }

    /**
     * @param newParentId 新父节点 ID
     */
    public void setNewParentId(Long newParentId) {
        this.newParentId = newParentId;
    }
}
