package com.ht.tools.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 用途：仅在 local 环境放行冒烟接口，便于本地连通性验证。
 * 职责/边界：仅覆盖 /api/infra/ping 与 /api/cache/test，其他接口仍由主安全配置控制。
 * 调用时机：Spring Boot 启动且激活 local profile 时生效。
 * 线程模型：无状态配置类，线程安全。
 * 关系说明：与主安全过滤链并存，优先级更高以便局部放行。
 */
@Configuration
@Profile("local")
public class LocalInfraSecurityConfig {

    /**
     * 行为：为 /api/infra/ping 与 /api/cache/test 单独设置放行规则。
     * 入参：HttpSecurity 安全构建器。
     * 出参：SecurityFilterChain。
     * 异常：配置异常时抛出 Exception。
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 配置异常
     */
    @Bean
    @Order(0)
    public SecurityFilterChain localInfraSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/infra/ping", "/api/cache/test");
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }
}
