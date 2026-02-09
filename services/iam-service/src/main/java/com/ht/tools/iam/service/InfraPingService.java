package com.ht.tools.iam.service;

import com.ht.tools.iam.api.dto.InfraPingResponse;

/**
 * 用途：提供 infra 冒烟接口的业务入口，验证 MyBatis+MySQL 连接。
 * 职责/边界：封装对 Mapper 的访问，不暴露持久层实体到 Controller。
 * 调用时机：由 InfraPingController 调用。
 * 线程模型：无状态 Service，线程安全。
 * 关系说明：依赖 InfraPingMapper，返回 InfraPingResponse DTO。
 */
public interface InfraPingService {

    /**
     * 行为：获取 infra 冒烟信息（计数/固定消息）。
     * 入参：无。
     * 出参：InfraPingResponse。
     * 异常：数据库不可用时由框架抛出异常并统一处理。
     *
     * @return InfraPingResponse
     */
    InfraPingResponse getPing();
}
