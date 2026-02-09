package com.ht.tools.iam.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 用途：infra_ping 表的持久化实体，承载最小冒烟数据。
 * 职责/边界：仅用于持久层映射，不作为 API DTO 或业务对象直接返回。
 * 调用时机：由 Mapper 读写数据库时创建与填充。
 * 线程模型：POJO 无状态，线程安全由使用方保证。
 * 关系说明：与 InfraPingMapper 对应，供 InfraPingService 使用。
 */
@TableName("infra_ping")
public class InfraPingEntity {

    /**
     * 主键 ID。
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 冒烟消息。
     */
    private String msg;

    /**
     * 创建时间。
     */
    private LocalDateTime createdAt;

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
     * @return 冒烟消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg 冒烟消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
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
}
