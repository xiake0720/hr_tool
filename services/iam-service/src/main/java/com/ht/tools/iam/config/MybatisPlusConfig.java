package com.ht.tools.iam.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用途：统一配置 MyBatis-Plus 生态能力（分页插件、Mapper 扫描）。
 * 职责/边界：仅做框架级持久层配置，不承载业务逻辑与数据访问细节。
 * 调用时机：Spring 启动时加载配置，提供 Mapper 扫描与插件注册。
 * 线程模型：无状态配置类，线程安全。
 * 关系说明：为 infra 层 Mapper 提供扫描入口，配合 MyBatis-Plus 拦截器。
 */
@Configuration
@MapperScan("com.ht.tools.iam.infrastructure.mapper")
public class MybatisPlusConfig {

    /**
     * 行为：注册 MyBatis-Plus 插件，启用分页拦截器。
     * 入参：无。
     * 出参：MybatisPlusInterceptor 实例。
     * 异常：无。
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
