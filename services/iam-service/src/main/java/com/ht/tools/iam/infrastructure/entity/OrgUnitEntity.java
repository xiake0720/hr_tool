package com.ht.tools.iam.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 用途：组织架构表持久化实体。
 * 职责/边界：仅用于数据库映射，不作为 API DTO 返回。
 * 调用时机：Mapper 读写组织表时使用。
 * 线程模型：POJO，无状态。
 */
@TableName("org_unit")
public class OrgUnitEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("parent_id")
    private Long parentId;

    private String path;
    private String name;
    private String type;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("created_by")
    private Long createdBy;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("updated_by")
    private Long updatedBy;

    /**
     * @return 主键 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 主键 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 租户 ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId 租户 ID
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

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
     * @return 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
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

    /**
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt 创建时间
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return 创建人
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 创建人
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 更新时间
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return 更新人
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
