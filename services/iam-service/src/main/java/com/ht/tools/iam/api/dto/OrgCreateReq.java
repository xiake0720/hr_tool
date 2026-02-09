package com.ht.tools.iam.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用途：组织节点创建请求 DTO。
 * 职责/边界：仅用于 API 输入，不参与持久化。
 * 调用时机：POST /api/org-units 入参。
 * 线程模型：不可变 DTO，线程安全。
 */
public class OrgCreateReq {

    private Long parentId;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    /**
     * @return 父节点 ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId 父节点 ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return 组织名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 组织名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 组织类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 组织类型
     */
    public void setType(String type) {
        this.type = type;
    }
}
