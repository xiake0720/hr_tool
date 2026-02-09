package com.ht.tools.iam.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.tools.iam.infrastructure.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用途：用户表 Mapper。
 * 职责/边界：仅负责 SQL 映射，不承载业务逻辑。
 * 调用时机：Service 层调用进行持久化操作。
 * 线程模型：Mapper 由 MyBatis 管理，线程安全由框架保证。
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
