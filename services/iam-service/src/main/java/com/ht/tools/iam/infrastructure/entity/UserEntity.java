package com.ht.tools.iam.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 用途：用户表持久化实体。
 * 职责/边界：仅用于数据库映射，不作为 API DTO 返回。
 * 调用时机：Mapper 读写用户表时使用。
 * 线程模型：POJO，无状态。
 */
@TableName("`user`")
public class UserEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("external_sub")
    private String externalSub;

    @TableField("login_name")
    private String loginName;

    private String password;
    private String username;
    private String email;
    private String phone;
    private Boolean enabled;

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
     * @return 外部身份标识
     */
    public String getExternalSub() {
        return externalSub;
    }

    /**
     * @param externalSub 外部身份标识
     */
    public void setExternalSub(String externalSub) {
        this.externalSub = externalSub;
    }

    /**
     * @return 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return 密码哈希
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 密码哈希
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 是否启用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled 是否启用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
