package com.ht.tools.iam.api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：组织树节点响应 DTO。
 * 职责/边界：仅用于 API 输出，不参与持久化。
 * 调用时机：GET /api/org-units/tree 返回时使用。
 * 线程模型：节点对象按请求构建，线程安全。
 */
public class OrgTreeResp {

    private final Long id;
    private final Long parentId;
    private final String name;
    private final String type;
    private final String path;
    private final List<OrgTreeResp> children = new ArrayList<>();

    /**
     * 行为：构造组织树节点 DTO。
     *
     * @param id 节点 ID
     * @param parentId 父节点 ID
     * @param name 名称
     * @param type 类型
     * @param path 路径
     */
    public OrgTreeResp(Long id, Long parentId, String name, String type, String path) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
        this.path = path;
    }

    /**
     * @return 节点 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return 父节点 ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * @return 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * @return 子节点列表
     */
    public List<OrgTreeResp> getChildren() {
        return children;
    }
}
