package com.ht.tools.iam.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.tools.iam.infrastructure.entity.InfraPingEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用途：infra_ping 表的数据访问 Mapper。
 * 职责/边界：仅负责 SQL 映射与数据访问，不承载业务规则。
 * 调用时机：由 Service 层调用进行冒烟读写。
 * 线程模型：Mapper 由 MyBatis 管理，线程安全由框架保证。
 * 关系说明：配合 MybatisPlusConfig 的 @MapperScan 使用。
 */
@Mapper
public interface InfraPingMapper extends BaseMapper<InfraPingEntity> {
}
