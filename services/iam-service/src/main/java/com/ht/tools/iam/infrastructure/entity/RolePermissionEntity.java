package com.ht.tools.iam.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 用途：角色与权限关系表持久化实体。
 * 职责/边界：仅用于数据库映射，不作为 API DTO 返回。
 * 调用时机：Mapper 读写关系表时使用。
 * 线程模型：POJO，无状态。
 */
@TableName("role_permission")
public class RolePermissionEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;

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
     * @return 角色 ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId 角色 ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return 权限 ID
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * @param permissionId 权限 ID
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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
