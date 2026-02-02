package com.ht.tools.iam.common.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用途：Web 层通用组件配置，注册基础 Filter。
 * 边界：仅注册 Web 相关 Bean，不包含业务配置。
 * 线程模型：配置类无状态，线程安全。
 * 调用时机：Spring 容器启动时加载。
 * 清理责任：无。
 * 误用风险：在此配置业务 Bean 会导致职责混乱。
 * 与其它组件关系：为 {@link RequestIdFilter} 提供 Bean。
 */
@Configuration
public class WebConfig {

    /**
     * 入参：无。
     * 返回：RequestIdFilter Bean。
     * 副作用：注册过滤器到 Spring 容器。
     * 是否可空：不返回 null。
     * 异常：无。
     * 线程安全：线程安全（Spring 管理单例）。
     * 使用建议：如需扩展过滤器顺序，使用 FilterRegistrationBean。
     *
     * @return RequestIdFilter
     */
    @Bean
    public RequestIdFilter requestIdFilter() {
        return new RequestIdFilter();
    }
}