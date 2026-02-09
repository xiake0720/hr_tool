package com.ht.tools.iam.service.impl;

import com.ht.tools.iam.api.dto.InfraPingResponse;
import com.ht.tools.iam.infrastructure.mapper.InfraPingMapper;
import com.ht.tools.iam.service.InfraPingService;
import org.springframework.stereotype.Service;

/**
 * 用途：infra 冒烟业务实现，聚合 mapper 的访问。
 * 职责/边界：只做简单查询与 DTO 组装，不包含复杂业务规则。
 * 调用时机：由 InfraPingController 调用。
 * 线程模型：无状态 Service，线程安全。
 * 关系说明：依赖 InfraPingMapper，返回 InfraPingResponse。
 */
@Service
public class InfraPingServiceImpl implements InfraPingService {

    private final InfraPingMapper infraPingMapper;

    public InfraPingServiceImpl(InfraPingMapper infraPingMapper) {
        this.infraPingMapper = infraPingMapper;
    }

    /**
     * 行为：统计 infra_ping 表记录数并返回固定消息。
     * 入参：无。
     * 出参：InfraPingResponse，包含计数与 pong 文本。
     * 异常：数据库异常由框架抛出并由全局异常处理器处理。
     *
     * @return InfraPingResponse
     */
    @Override
    public InfraPingResponse getPing() {
        long count = infraPingMapper.selectCount(null);
        return new InfraPingResponse("pong", count);
    }
}
